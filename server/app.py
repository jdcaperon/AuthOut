from db import app
import views

# Register sub views.
app.register_blueprint(views.Heartbeat.bp)
app.register_blueprint(views.Kiosk.bp)
app.register_blueprint(views.Parent.bp)
app.register_blueprint(views.Child.bp)
app.register_blueprint(views.Entry.bp)
app.register_blueprint(views.Admin.bp)

if __name__ == '__main__':
    app.run()
