import functools
from flask import Blueprint, jsonify, request
from models.HeartbeatModel import HeartbeatModel

bp = Blueprint('heartbeat', __name__, url_prefix="/kiosk")


@bp.route('/', methods=['GET', 'POST'])
def model_endpoint():
  if request.method == 'POST':
    # Build a new Kiosk login
    data: object = {}
    data["Name"] = "Fred"
    data["authkey"] = "Im a base64 encoded authorization key"
    return jsonify(data.__dict__)
  else:
    # A get request will collect information about this lovely kiosk

@bp.route('/', methods=['POST'])
def signin_endpoint():
  """
  Returns a parent object with children attached if the image given matches 
  someone that can be logged into this kiosk.
  """
  # We only allow POSTs to this endpoint
  data = request.get_json(force=True)
  # todo: check for required fields
  print(data)
  # * for jack to add in the amazon stuff here, not too sure on it.
  