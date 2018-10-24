import base64
from flask import Blueprint, jsonify, request, Response

from models.EntryModel import EntryModel
from models.aws import get_id_by_image, set_parent_photo
from db import db
from models.ParentModel import ParentModel
from models.ChildModel import ChildModel
from models.OTPModel import OTPModel
from twilio.twiml.messaging_response import MessagingResponse
from random import randint
from twilio.rest import Client
import os

bp = Blueprint('kiosk', __name__, url_prefix="/kiosk")


@bp.route('/', methods=['GET', 'POST'])
def model_endpoint():
    """
    Main endpoint for querying the kiosks information or creating a kiosk.
    :return:
    """
    if request.method == 'POST':
        # Build a new Kiosk login
        data: object = {"Name": "Fred", "authkey": "Im a base64 encoded authorization key"}
        return jsonify(data.__dict__)
    else:
        return ""


@bp.route('/login', methods=['POST'])
def login_endpoint():
    """
    Returns a parent object with children attached if the image given matches
    someone that can be logged into this kiosk.
    """
    # We only allow POSTs to this endpoint
    data = request.get_json(force=True)
    print(data)
    if "user_photo" in data:
        photo = data['user_photo']
        print(photo)
        parent_id = get_id_by_image(photo)
        parent = db.session.query(ParentModel).filter_by(id=parent_id)
        if parent.count() == 1:
            return jsonify((parent.first()).as_dict())
    return Response('', 400)


@bp.route('/code', methods=['POST'])
def generate_code_endpoint():
    """Respond to incoming calls with a simple text message."""
    resp = MessagingResponse()

    body = request.values.get('From', None)
    local_number = "0" + body[3:]
    parent = db.session.query(ParentModel).filter_by(mobile_number=local_number)
    parent_id = parent.first().as_dict()["id"]

    if parent.count() == 1:
        # here we generate a code for them and add it to the Db
        code = randint(1000, 9999)
        available_code = db.session.query(OTPModel).filter_by(code=code)

        # find a new code if it's not unique
        while available_code.count() != 0:
            code = randint(1000, 9999)
            available_code = db.session.query(OTPModel).filter_by(code=code)

        current_code_location = db.session.query(OTPModel).filter_by(parent_id=parent_id)
        db.session.delete(current_code_location.first())
        db.session.commit()

        otp = OTPModel()
        data = {"code": code, "parent_id": parent_id}
        valid = otp.load(data)

        if valid:
            db.session.add(otp)
            db.session.commit()
            resp.message("Your AuthOut code is " + str(code) + ".")
        else:
            resp.message("Error creating AuthOut code. Please try again.")
    else:
        resp.message("You've messaged the Admin verification system for AuthOut, if this was intended "
                     "please contact an admin to register yourself in the system.")

    return str(resp)


@bp.route('/signin_code', methods=['GET','POST'])
def code_sign_in_endpoint():
    if request.method == 'POST':
        data = request.get_json(force=True)
        code = data["code"]
        code_entry = db.session.query(OTPModel).filter_by(code=code)

        if code_entry.count() == 1:
            parent_id = code_entry.first().parent_id
            parent = db.session.query(ParentModel).filter_by(id=parent_id)
            if parent.count() == 1:
                return jsonify((parent.first()).as_dict())
        else:
            return Response('Invalid code', 400)
    else:
        container = []
        codes = db.session.query(OTPModel).order_by(OTPModel.parent_id)
        for code in codes:
            container.append(code.as_dict())
        return jsonify(container)


def send_text(mobile_number, body):
    #mobile in the form 0411878988
    mobile_number = "+61" + mobile_number[1:]
    account_sid = str(os.environ['TWILIO_ACCOUNT_SID'])
    auth_token = str(os.environ['TWILIO_ACCOUNT_TOKEN'])
    authout_mobile = str(os.environ['AUTHOUT_MOBILE_NUMBER'])

    client = Client(account_sid, auth_token)
    client.messages.create(
        from_=authout_mobile,
        body=body,
        to=mobile_number
    )

@bp.route('/signin', methods=['POST'])
def signin_endpoint():
    data = request.get_json(force=True)

    for i in range(0, len(data['children'])):
        child_data = data['children'][i]
        individual = {'parent_id': data['parent_id'],
                      'child_id': child_data['id'],
                      'status': child_data['status']}
        entry = EntryModel()
        if entry.load(individual):
            child = db.session.query(ChildModel).filter_by(id=entry.child_id)
            parent = db.session.query(ParentModel).filter_by(id=entry.parent_id)

            ''
            parents = child.first().guardians
            for guardian in parents:
                mobile = guardian.mobile_number
                status = "signed out." if child_data['status'] is False else "signed in."
                text_body = str(child.first_name) + " has been " + status
                send_text(mobile, text_body)


            if child.count() == 1 and parent.count() == 1:
                child = child.first()
                child.status = entry.status

                db.session.add(entry)
                db.session.add(child)
                db.session.commit()
            else:
                return Response('No parent or child matched', 400)
        else:
            return Response('Json Package did not contain required keys', 400)


    return Response('{}', 200)


@bp.route('/register', methods=['POST'])
def register_endpoint():
    data = request.get_json(force=True)
    print(data)

    if "parent" in data:
        parent_data = data['parent']
        parent = ParentModel()
        valid_parent = parent.load(parent_data)

        if not valid_parent:
            return Response('Parent was not valid ( missing required data )', 400)

        children_ids = []
        if "children" in data:
            children_list = data["children"]
            children = children_list
            for child_data in children:
                child = ChildModel()
                child_valid = child.load(child_data)
                if child_valid:
                    db.session.add(child)
                    db.session.commit()
                    children_ids.append(child.id)

            for i in children_ids:
                child = db.session.query(ChildModel).filter_by(id=i).first()
                if child is not None:
                    parent.children.append(child)

            if valid_parent:
                print('parent valid and committed')
                db.session.add(parent)
                db.session.commit()
            else:
                print('parent is not valid and wasnt committed')
                print(parent_data)

        if "user_photo" in data:
            print("The parent id is : {}".format(parent.id))
            set_parent_photo(parent.id, base64.decodestring(bytes(data['user_photo'], 'ASCII')))
            return jsonify({'id': parent.id})
        else:
            return Response('user_photo invalid or not provided', 400)

    return Response('Missing parent data', 400)
