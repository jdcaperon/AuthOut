import json
from db import db


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

    required_keys = ["email", "first_name", "last_name", "date_of_birth", "mobile_number"]

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
            "image_id": self.image_id
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
            if i == "date_of_birth":
                setattr(self, i, datetime.strptime(data[i], '%d/%m/%Y'))
            else:
                setattr(self, i, data[i])
        print(self)
        return True
