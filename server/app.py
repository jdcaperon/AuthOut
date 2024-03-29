from flask import Response, jsonify, request

from models.AdminModel import AdminModel
from views.Auth import requires_auth

from db import app, db
import views

# Register sub views.

app.register_blueprint(views.Heartbeat.bp)
app.register_blueprint(views.Kiosk.bp)
app.register_blueprint(views.Parent.bp)
app.register_blueprint(views.Child.bp)
app.register_blueprint(views.Entry.bp)
app.register_blueprint(views.Admin.bp)


@app.route('/login')
@requires_auth
def login():
    auth = request.authorization
    admin = db.session.query(AdminModel).filter_by(email=auth.username).first()
    return jsonify(admin.as_dict())


if __name__ == '__main__':
    app.run()
