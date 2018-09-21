from db import db

# Guardian Relationship
guardian_table = db.Table(
    'guardian',
    db.Column('parent_id', db.Integer, db.ForeignKey('parents.id')),
    db.Column('child_id', db.Integer, db.ForeignKey('children.id'))
)

# Trusted Guardian Relationship
trusted_guardian_table = db.Table(
    'trusted_guardian',
    db.Column('parent_id', db.Integer, db.ForeignKey('parents.id')),
    db.Column('child_id', db.Integer, db.ForeignKey('children.id'))
)

