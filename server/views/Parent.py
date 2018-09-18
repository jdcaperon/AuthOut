import functools
from flask import Blueprint, jsonify
from models.HeartbeatModel import HeartbeatModel

bp = Blueprint('heartbeat', __name__, url_prefix="/parent")


@bp.route('/', methods=['GET', 'POST'])
def core():
  if request.method == 'POST':
    # read the data given and create a parent.
  else:
    # list all the parents.
  
  data: HeartbeatModel = HeartbeatModel()
  return jsonify(data.__dict__)