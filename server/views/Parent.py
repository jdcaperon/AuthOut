import base64

from flask import Blueprint, jsonify, request, Response
from db import db
from models.ParentModel import ParentModel
from models.ChildModel import ChildModel
from models.aws import get_parent_photo, set_parent_photo, delete_parent_photo

bp = Blueprint('parent', __name__, url_prefix="/parent")


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
            container.append(parent.as_dict())
        return jsonify(container)


@bp.route('/<int:parent_id>', methods=['GET', 'PUT', 'DELETE'])
def specified(parent_id):
    """
    Endpoint for creating, updating and deleting parents.
    """
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
        delete_parent_photo(parent.id)
        return Response('', 200)
    # Default case.
    else:
        return Response('', 404)


@bp.route('/<int:parent_id>/photo', methods=['GET', 'PUT', 'DELETE'])
def photo(parent_id):
    """
    Endpoint for creating, updating and deleting parent photos.
    """
    parent = db.session.query(ParentModel).filter_by(id=parent_id)
    if parent.count() != 1:
        return Response('No Parent Matching that ID', 400)
    parent = parent.first()
    # Gets the information of a specific parent.
    if request.method == 'GET':
        return jsonify({'user_image': get_parent_photo(parent_id).decode('ASCII')})
    # Updates a specific parent
    elif request.method == 'PUT':
        data = request.get_json(force=True)
        if 'user_photo' in data:
            set_parent_photo(parent_id, base64.decodestring(bytes(data['user_photo'], 'ASCII')))
            return Response('', 200)
    # Deletes a particular parent
    elif request.method == 'DELETE':
        delete_parent_photo(parent_id)
        return Response('', 200)
    # Default case.
    else:
        return Response('', 404)
    return Response('', 400)


@bp.route('/<int:parent_id>/children', methods=['GET', 'POST', 'DELETE'])
def children(parent_id):
    """
    Sub endpoint to get children of the parents. Endpoint includes getting the children,
    Adding children through POST and deleting children.
    """
    parent = db.session.query(ParentModel).filter_by(id=parent_id)
    if parent.count() != 1:
        return Response('', 400)
    parent = parent.first()
    # Gets the information of a specific parent.
    if request.method == 'GET':
        holder = {"children": parent.as_dict()["children"]}
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
                child = db.session.query(ChildModel).filter_by(id=i).first()
                parent.children.remove(child)
        db.session.add(parent)
        db.session.commit()
        return Response('', 200)
    # Default case.
    else:
        return Response('', 404)


@bp.route('/<int:parent_id>/children/trusted', methods=['GET', 'POST', 'DELETE'])
def trusted_children(parent_id):
    """
    Sub endpoint to get trusted children of the parents. Endpoint includes getting the children,
    Adding children through POST and deleting children.
    """
    parent = db.session.query(ParentModel).filter_by(id=parent_id)
    if parent.count() != 1:
        return Response('', 400)
    parent = parent.first()
    # Gets the information of a specific parent.
    if request.method == 'GET':
        holder = {"children": parent.as_dict()["trusted_children"]}
        return jsonify(holder)
    # Updates a specific parent
    elif request.method == 'POST':
        data = request.get_json(force=True)
        if "children" in data:
            for i in data["children"]:
                child = db.session.query(ChildModel).filter_by(id=i).first()
                if child is not None:
                    parent.trusted_children.append(child)
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
                child = db.session.query(ChildModel).filter_by(id=i).first()
                parent.trusted_children.remove(child)
        db.session.add(parent)
        db.session.commit()
        return Response('', 200)
    # Default case.
    else:
        return Response('', 404)


