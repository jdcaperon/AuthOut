import functools
from flask import Blueprint, jsonify
from models.HeartbeatModel import HeartbeatModel

bp = Blueprint('heartbeat', __name__, url_prefix="/heartbeat")


@bp.route('/')
def heartbeat():
  data: HeartbeatModel = HeartbeatModel()
  return jsonify(data.__dict__)