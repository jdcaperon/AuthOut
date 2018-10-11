import base64
from flask import Blueprint, jsonify, request, Response
from models.aws import get_id_by_image, set_parent_photo
from db import db
from models.ParentModel import ParentModel
from models.ChildModel import ChildModel
import json

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
def signin_endpoint():
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


@bp.route('/register', methods=['POST'])
def register_endpoint():
    data = request.get_json(force=True)
    print(data)

    if "parent" in data:
        parent_data = data['parent']
        parent = ParentModel()
        valid_parent = parent.load(parent_data)

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
                db.session.add(parent)
                db.session.commit()

        if "user_photo" in data:
            set_parent_photo(parent.id, base64.decodestring(bytes(data['user_photo'], 'ASCII')))
            return Response('', 200)

    return Response('', 400)





