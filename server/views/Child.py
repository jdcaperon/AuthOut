from flask import Blueprint, jsonify, request, Response
from db import db
from models.ChildModel import ChildModel

bp = Blueprint('child', __name__, url_prefix="/child")


@bp.route('/', methods=['GET', 'POST'])
def core():
    """
    Core endpoint for children:

    GET => returns a list of children.
    POST => allows creation of a child.
    """
    if request.method == 'POST':
        # read the data given and create a parent.
        data = request.get_json(force=True)
        child = ChildModel()
        valid = child.load(data)
        if valid:
            db.session.add(child)
            db.session.commit()
            response = {"id": child.id}
            return jsonify(response)
        return Response('', 400, {})
    else:
        # list all the parents.
        container = []
        children = db.session.query(ChildModel).order_by(ChildModel.id)
        for child in children:
            container.append(child.as_dict())
        return jsonify(container)
