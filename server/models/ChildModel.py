from db import db
from datetime import datetime

import json


class ChildModel(db.Model):
    __tablename__ = 'children'
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String)
    last_name = db.Column(db.String)
    date_of_birth = db.Column(db.Date)
    status = db.Column(db.Boolean, default=False)

    required_keys = ["first_name", "last_name", "date_of_birth"]

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
            "id": self.id,
            "first_name": self.first_name,
            "last_name": self.last_name,
            "date_of_birth": self.date_of_birth.strftime('%d/%m/%Y'),
            "status": self.status
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
        if check and  not self.required(data):
            return False
        for i in self.required_keys:
            if i == "date_of_birth":
                setattr(self, i, datetime.strptime(data[i], '%d/%m/%Y'))
            else:
                setattr(self, i, data[i])
        print(self)
        return True
