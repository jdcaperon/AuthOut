import sys
from datetime import date, datetime, timedelta

from dateutil.rrule import rrule, DAILY
from flask import Blueprint, jsonify, request
from sqlalchemy import cast, Date, func

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
    lower = datetime.utcnow().date()
    upper = datetime.utcnow().date() + timedelta(days=1)
    sys.stderr.write(date.today().strftime('%d/%m/%Y'))
    entries = db.session.query(EntryModel)\
        .filter(EntryModel.time >= lower)\
        .filter(EntryModel.time <= upper) \
        .order_by(EntryModel.time)
    entries_json = []
    for entry in entries:
        print("entry {}".format(entry))
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


@bp.route('/query', methods=['GET', 'POST'])
def query():
    data = request.get_json(force=True)
    lower = datetime.strptime(data['lower'], '%d/%m/%Y')
    upper = datetime.strptime(data['upper'], '%d/%m/%Y') + timedelta(days=1)
    entries = db.session.query(EntryModel)\
        .filter(EntryModel.time >= lower)\
        .filter(EntryModel.time <= upper)\
        .filter_by(child_id=data['id'])\
        .filter_by(status=True)\
        .order_by(EntryModel.time)

    print(entries)
    output = []
    for entry in entries.all():
        output.append(entry.as_dict())
    return jsonify({'entries': output})

@bp.route('/stats', methods=['GET', 'POST'])
def stats():
    data = request.get_json(force=True)
    lower = datetime.strptime(data['lower'], '%d/%m/%Y')
    upper = datetime.strptime(data['upper'], '%d/%m/%Y') + timedelta(days=1)

    data = {'days': []}
    for dt in rrule(DAILY, dtstart=lower, until=upper):
        day = {'date': dt.strftime("%d/%m/%Y"), 'signins': 0, 'entries': []}
        # attendance count
        signed_in = db.session.query(func.Count(EntryModel.child_id))\
            .filter(EntryModel.time >= dt)\
            .filter(EntryModel.time <= (dt + timedelta(days=1)))\
            .filter_by(status=True)\
            .group_by(EntryModel.child_id).count()
        day['signins'] = signed_in

        entries = db.session.query(EntryModel)\
            .filter(EntryModel.time >= dt)\
            .filter(EntryModel.time <= (dt + timedelta(days=1)))
        for entry in entries:
            day['entries'].append(entry.as_dict())

        data['days'].append(day)

    return jsonify(data)
