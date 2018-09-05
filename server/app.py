from flask import Flask
from flask_sqlalchemy import SQLAlchemy 
import views
import os

app = Flask(__name__)
app.config.from_object(os.environ['APP_SETTINGS'])
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

## Register sub views.
#   /heartbeat
app.register_blueprint(views.Heartbeat.bp)

db = SQLAlchemy(app)

if __name__ == '__main__':
  app.run()