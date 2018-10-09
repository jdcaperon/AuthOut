from flask import Blueprint, jsonify, request, Response
from models.aws import get_id_by_image
from db import db
from models.ParentModel import ParentModel

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
