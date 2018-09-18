import json
from app import db

class ParentModel(db.Model):
  __tablename__ = 'parents'
  id = db.Column(db.Integer, primary_key=True)
  email = db.Column(db.String)
  first_name = db.Column(db.String)
  last_name = db.Column(db.String)
  date_of_birth = db.Column(Date)
  mobile_number = db.Column(db.String)
  image_id = db.Column(db.Integer)

  def __repr__(self):
    return json.dumps(self.__dict__)