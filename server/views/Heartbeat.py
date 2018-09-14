import functools
from flask import Blueprint, jsonify
from models.HeartbeatModel import HeartbeatModel
from .Auth import requires_auth, requires_access_level

bp = Blueprint('heartbeat', __name__, url_prefix="/heartbeat")


@bp.route('/')
def heartbeat():
  data: HeartbeatModel = HeartbeatModel()
  return jsonify(data.__dict__)

@bp.route("/auth")
@requires_auth
def auth_test():
  return "hello"

@bp.route("/permission/kiosk")
@requires_access_level("kiosk")
def permission_kiosk():
  return "Youve got kiosk permissions"