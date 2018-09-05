https://realpython.com/flask-by-example-part-2-postgres-sqlalchemy-and-alembic/

# AuthOut

## Installation

### Manual

Installing requirements:
  1. `python3.6 -m venv env`
  2. `source env/bin/activate`
  3. `pip install -r requirements`

### Automatic

`echo "source `which activate.sh`" >> ~/.bashrc`
`source ~/.bashrc`

### Database

Installing database:

`initdb -D database/data`

Starting the database:

`postgres -D database/data`

Migrating the database:

  1. `python manage.py db init`
  2. `python manage.py db migrate`
  3. `python manage.py db upgrade`


