from flask import Blueprint, jsonify, request, Response
from db import db
from models.ParentModel import ParentModel

bp = Blueprint('parent', __name__, url_prefix="/parent")


@bp.route('/', methods=['GET', 'POST'])
def core():
    if request.method == 'POST':
        # read the data given and create a parent.
        data = request.get_json(force=True)
        parent = ParentModel()
        valid = parent.load(data)
        if valid:
            db.session.add(parent)
            db.session.commit()
            response = {"id": parent.id}
            return jsonify(response)
        return Response('', 400, {})
    else:
        # list all the parents.
        container = []
        parents = db.session.query(ParentModel).order_by(ParentModel.id)
        for parent in parents:
            print(parent.email)
            container.append(parent.as_dict())
        return jsonify(container)
