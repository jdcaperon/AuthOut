from db import db
from datetime import datetime

import json


class ParentModel(db.Model):
    """
    Parent Entity from ER model.
    """
    __tablename__ = 'parents'
    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String, primary_key=True)
    first_name = db.Column(db.String, primary_key=True)
    last_name = db.Column(db.String, primary_key=True)
    date_of_birth = db.Column(db.Date)
    mobile_number = db.Column(db.String)

    # Relationships
    children = db.relationship('ChildModel', secondary='guardian', backref=db.backref('guardians'))
    trusted_children = db.relationship('ChildModel',
                                       secondary="trusted_guardian",
                                       backref=db.backref('trusted_guardians'))

    required_keys = ["email", "first_name", "last_name", "date_of_birth", "mobile_number"]
    extra_keys = ["children", "trusted_children"]

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
            "email": self.email,
            "first_name": self.first_name,
            "last_name": self.last_name,
            "date_of_birth": self.date_of_birth.strftime('%d/%m/%Y'),
            "mobile_number": self.mobile_number,
            "children": self.convert_children(self.children),
            "trusted_children": self.convert_children(self.trusted_children)
        }

    def convert_children(self, children):
        """
        Converts children to a collection of children.
        :param children: Children models to convert.
        :return: a collection of children as dictionaries.
        """
        collection = []
        for child in children:
            collection.append(child.as_dict())
        return collection

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
                if i == "date_of_birth":
                    setattr(self, i, datetime.strptime(data[i], '%d/%m/%Y'))
                elif i == "children" or i == "trusted_children":
                    print(data[i])
                else:
                    setattr(self, i, data[i])
        print(self)
        return True
