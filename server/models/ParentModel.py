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

    def __repr__(self):
        return json.dumps(self.__dict__)

    def as_dict(self):
        return {
          "id": self.id,
          "email": self.email
        }

    def load(self, data: dict):

        return True
