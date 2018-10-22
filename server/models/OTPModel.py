from db import db
from datetime import datetime
from werkzeug.security import generate_password_hash, check_password_hash

import json


class OTPModel(db.Model):
    __tablename__ = 'otp'

    code = db.Column(db.Integer)
    parent_id = db.Column(db.Integer, db.ForeignKey('parents.id'), primary_key=True)

    required_keys = ["code", "parent_id"]

    def __repr__(self):
        """
        Representation of the model.
        :return: json version of the model.
        """
        return json.dumps(self.as_dict())

    def as_dict(self):
        """
        Representation of the model as a standard dictionary.
        """
        return {
            "code": self.code,
            "parent_id": self.id
        }

    def required(self, data: dict):
        """
        Checks the dictionary has all required keys.
        """
        for i in self.required_keys:
            if i not in data:
                return False
        return True

    def load(self, data: dict, check=True):
        """
        Loads a dictionaries values into the model.
        """
        if check and not self.required(data):
            return False
        for i in data:
            setattr(self, i, data[i])
        print(self)
        return True
