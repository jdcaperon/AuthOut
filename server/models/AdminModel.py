from db import db
from datetime import datetime
from werkzeug.security import generate_password_hash, check_password_hash

import json


class AdminModel(db.Model):
    __tablename__ = 'admin'
    email = db.Column(db.String, primary_key=True)
    name = db.Column(db.String)
    password = db.Column(db.String)

    required_keys = ["email", "name", "password"]

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
            "email": self.email,
            "name": self.name
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
            if i in self.required_keys or i in self.extra_keys:
                if i == 'password':
                    self.set_password(data['password'])
                else:
                    setattr(self, i, data[i])
        print(self)
        return True

    def set_password(self, password):
        self.password = generate_password_hash(password)

    def check_password(self, password):
        return check_password_hash(self.password, password)
