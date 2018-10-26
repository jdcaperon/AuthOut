import base64

from flask import Blueprint, jsonify, request, Response
from db import db
from models.AdminModel import AdminModel

bp = Blueprint('admin', __name__, url_prefix="/admin")


@bp.route('/', methods=['GET', 'POST'])
def core():
    """
    Core endpoint for children:

    GET => returns a list of admins.
    POST => allows creation of a admin.
    """
    if request.method == 'POST':
        # read the data given and create a parent.
        data = request.get_json(force=True)
        admin = AdminModel()
        valid = admin.load(data)
        if valid:
            try:
                db.session.add(admin)
                db.session.commit()
                return Response('Created Admin', 200)
            except Exception:
                return Response('Unable to create account', 200)
        return Response('Invalid data sent to server', 400, {})
    else:
        # list all the parents.
        container = []
        admins = db.session.query(AdminModel).order_by(AdminModel.email)
        for admin in admins:
            container.append(admin.as_dict())
        return jsonify(container)


