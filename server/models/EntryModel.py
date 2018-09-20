from db import db
from datetime import datetime, timezone

import json


class EntryModel(db.Model):
    __tablename__ = 'entry'
    id = db.Column(db.Integer, primary_key=True)
    parent_id = db.Column(db.Integer, db.ForiegnKey('parents.id'), primary_key=True)
    child_id = db.Column(db.Integer, db.ForiegnKey('children.id'), primary_key=True)
    time = db.Column(db.DateTime, default=datetime.utcnow, primary_key=True)

    required_keys = ["parent_id", "child_id"]

    def __repr__(self):
        return json.dumps(self.as_dict())

    def as_dict(self):
        return {
            "id": self.id,
            "parent_id": self.parent_id,
            "child_id": self.child_id,
            "time": self.time.replace(tzinfo=timezone.utc).timestamp()
        }

    def required(self, data: dict):
        for i in self.required_keys:
            if i not in data:
                return False
        return True

    def load(self, data: dict):
        if not self.required(data):
            return False
        for i in self.required_keys:
            setattr(self, i, data[i])
        print(self)
        return True
