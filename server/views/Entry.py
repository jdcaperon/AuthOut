from datetime import date

from flask import Blueprint, jsonify
from sqlalchemy import cast, Date

from db import db
from models.ParentModel import ParentModel
from models.ChildModel import ChildModel
from models.EntryModel import EntryModel

bp = Blueprint('entry', __name__, url_prefix="/entry")


@bp.route('/')
def live():
    """
    Default endpoint generated.
    """
    entries = db.session.query(EntryModel).filter(cast(EntryModel.time, Date) == date.today())
    entries_json = []
    for entry in entries:
        dict = entry.as_dict()
        parent = db.session.query(ParentModel).filter_by(id=dict['parent_id']).first()
        child = db.session.query(ChildModel).filter_by(id=dict['child_id']).first()
        dict['parent_name'] = '{} {}'.format(parent.first_name, parent.last_name)
        dict['child_name'] = '{} {}'.format(child.first_name, child.last_name)
        entries_json.append(dict)
    data = {'signed_in': db.session.query(ChildModel).filter_by(status=True).count(),
            'signed_out': db.session.query(ChildModel).filter_by(status=False).count(),
            'entries': entries_json}
    return jsonify(data)
