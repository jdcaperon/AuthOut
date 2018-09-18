from flask import Flask
from db import db, app
import views



## Register sub views.
#   /heartbeat
app.register_blueprint(views.Heartbeat.bp)
app.register_blueprint(views.Parent.bp)

if __name__ == '__main__':
  app.run()