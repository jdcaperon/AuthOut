from db import db
from datetime import datetime

import json


class ParentModel(db.Model):
    __tablename__ = 'parents'
    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String)
    first_name = db.Column(db.String)
    last_name = db.Column(db.String)
    date_of_birth = db.Column(db.Date)
    mobile_number = db.Column(db.String)

    # maybe just replace this with the base64 of the image.
    image_id = db.Column(db.Integer)

    # Relationships
    children = db.relationship('ChildModel', secondary='guardian', backref=db.backref('guardians'))
    trusted_children = db.relationship('ChildModel',
                                       secondary="trusted_guardian",
                                       backref=db.backref('trusted_guardians'))

    required_keys = ["email", "first_name", "last_name", "date_of_birth", "mobile_number"]
    extra_keys = ["children", "trusted_children"]

    def __repr__(self):
        return json.dumps(self.as_dict())

    def as_dict(self):
        return {
            "id": self.id,
            "email": self.email,
            "first_name": self.first_name,
            "last_name": self.last_name,
            "date_of_birth": self.date_of_birth.strftime('%d/%m/%Y'),
            "mobile_number": self.mobile_number,
            "image_id": self.image_id,
            "children": self.children,
            "trusted_children": self.trusted_children
        }

    def required(self, data: dict):
        for i in self.required_keys:
            if i not in data:
                return False
        return True

    def load(self, data: dict, check=True):
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
