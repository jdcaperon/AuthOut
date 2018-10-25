from flask import Blueprint, jsonify, request, Response
from db import db
from models.ChildModel import ChildModel
from models.ParentModel import ParentModel

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

            if 'parent_id' in data:
                parent = db.session.query(ParentModel).filter_by(id=data['parent_id']).first()
                if parent is None:
                    return Response('', 400, {})
                child = db.session.query(ChildModel).filter_by(id=child.id).first()
                if child is not None:
                    parent.children.append(child)
                db.session.add(parent)
                db.session.commit()

            return jsonify(response)
        return Response('', 400, {})
    else:
        # list all the parents.
        container = []
        children = db.session.query(ChildModel).order_by(ChildModel.id)

        for child in children:
            parents = []
            parents_query = db.session.query(ParentModel).order_by(ParentModel.id)
            for parent in parents_query:
                parent_dict = parent.as_dict()
                for temp_child in parent_dict['children']:
                    if temp_child['id'] == child.id:
                        full_name = parent_dict['first_name'] + " " + parent_dict['last_name']
                        parents.append(full_name)

            child_dict = child.as_dict()
            child_dict['parents'] = parents

            container.append(child_dict)
        return jsonify(container)


@bp.route('/<int:child_id>', methods=['GET', 'PUT', 'DELETE'])
def specified(child_id):
    """
    Endpoint for creating, updating and deleting children.
    """
    child = db.session.query(ChildModel).filter_by(id=child_id)
    if child.count() != 1:
        return Response('', 400)
    child = child.first()
    # Gets the information of a specific parent.
    if request.method == 'GET':

        parents = []
        parents_query = db.session.query(ParentModel).order_by(ParentModel.id)
        for parent in parents_query:
            parent_dict = parent.as_dict()
            for temp_child in parent_dict['children']:
                if temp_child['id'] == child_id:
                    full_name = parent_dict['first_name'] + " " + parent_dict['last_name']
                    parents.append(full_name)

        child_dict = child.as_dict()
        child_dict['parents'] = parents

        return jsonify(child_dict)
    # Updates a specific parent
    elif request.method == 'PUT':
        data = request.get_json(force=True)
        child.load(data, check=False)
        db.session.add(child)
        db.session.commit()
        return Response('', 200)
    # Deletes a particular parent
    elif request.method == 'DELETE':
        db.session.delete(child)
        db.session.commit()
        return Response('', 200)
    # Default case.
    else:
        return Response('', 404)