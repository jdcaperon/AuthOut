from app import db
from sqlalchemy.dialects.postgresql import JSON

class Result(db.Model):
  __tablename__ = 'results'

  id = db.Column(db.Integer, primary_key=True)
  
  def __repr__(self):
    return '<hello>'