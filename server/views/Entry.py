import functools
from flask import Blueprint, jsonify
from models.HeartbeatModel import HeartbeatModel

bp = Blueprint('heartbeat', __name__, url_prefix="/entry")


@bp.route('/')
def heartbeat():
    """
    Default endpoint generated.
    """
    data: HeartbeatModel = HeartbeatModel()
    return jsonify(data.__dict__)
