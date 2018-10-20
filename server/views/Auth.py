import functools
from flask import request, Response
from db import db
from models.AdminModel import AdminModel


def check_auth(username, password):
    """This function is called to check if a username /
    password combination is valid.
    """
    admin = db.session.query(AdminModel).filter_by(email=username).first()
    if admin is not None:
        return admin.check_password(password)
    return False


def authenticate():
    """Sends a 401 response that enables basic auth"""
    return Response(
        'Could not verify your access level for that URL.\n'
        'You have to login with proper credentials', 401,
        {'WWW-Authenticate': 'Basic realm="Login Required"'})


def requires_auth(f):
    @functools.wraps(f)
    def decorated(*args, **kwargs):
        auth = request.authorization
        if not auth or not check_auth(auth.username, auth.password):
            return authenticate()
        return f(*args, **kwargs)

    return decorated


def allowed(access_level, auth):
    """Checks to see if the user that's looked up has the required access level"""
    print(access_level)
    print(auth)
    # todo(evan)
    return False


def denied():
    """Sends a 559 response"""
    return Response(
        'You do not have the right access level for that URL.\n', 550, {})


def requires_access_level(access_level):
    def decorator(f):
        @functools.wraps(f)
        def decorated_function(*args, **kwargs):
            auth = request.authorization
            if not auth or not check_auth(auth.username, auth.password):
                return authenticate()
            elif not allowed(access_level, auth):
                return denied()
            return f(*args, **kwargs)

        return decorated_function

    return decorator
