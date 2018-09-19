from flask import Blueprint, jsonify, request, Response
from db import db
from models.ParentModel import ParentModel
from models.ChildModel import ChildModel

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
            print(parent.children)
            container.append(parent.as_dict())
        return jsonify(container)


@bp.route('/<int:parent_id>', methods=['GET', 'PUT', 'DELETE'])
def specified(parent_id):
    parent = db.session.query(ParentModel).filter_by(id=parent_id)
    if parent.count() != 1:
        return Response('', 400)
    parent = parent.first()
    # Gets the information of a specific parent.
    if request.method == 'GET':
        return jsonify(parent.as_dict())
    # Updates a specific parent
    elif request.method == 'PUT':
        data = request.get_json(force=True)
        parent.load(data, check=False)
        db.session.add(parent)
        db.session.commit()
        return Response('', 200)
    # Deletes a particular parent
    elif request.method == 'DELETE':
        db.session.delete(parent)
        db.session.commit()
        return Response('', 200)
    # Default case.
    else:
        return Response('', 404)


@bp.route('/<int:parent_id>/children', methods=['GET', 'POST', 'DELETE'])
def children(parent_id):
    parent = db.session.query(ParentModel).filter_by(id=parent_id)
    if parent.count() != 1:
        return Response('', 400)
    parent = parent.first()
    # Gets the information of a specific parent.
    if request.method == 'GET':
        holder = {"children:": parent.as_dict()["children"]}
        return jsonify(holder)
    # Updates a specific parent
    elif request.method == 'POST':
        data = request.get_json(force=True)
        if "children" in data:
            for i in data["children"]:
                child = db.session.query(ChildModel).filter_by(id=i).first()
                if child is not None:
                    parent.children.append(child)
            db.session.add(parent)
            db.session.commit()
            return Response('', 200)
        else:
            return Response('', 400)
    # Deletes a particular child
    elif request.method == 'DELETE':
        data = request.get_json(force=True)
        if "children" in data:
            for i in data["children"]:
                child = parent.children.filter_by(id=i).first()
                parent.children.remove(child)
        db.session.add(parent)
        db.session.commit()
        return Response('', 200)
    # Default case.
    else:
        return Response('', 404)


