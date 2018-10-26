"""empty message

Revision ID: 42ad30a7331c
Revises: 52a18c3c9806
Create Date: 2018-10-22 12:57:16.110106

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = '42ad30a7331c'
down_revision = '52a18c3c9806'
branch_labels = None
depends_on = None


def upgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.create_table('otp',
    sa.Column('code', sa.Integer(), nullable=True),
    sa.Column('parent_id', sa.Integer(), nullable=False),
    sa.ForeignKeyConstraint(['parent_id'], ['parents.id'], ),
    sa.PrimaryKeyConstraint('parent_id')
    )
    # ### end Alembic commands ###


def downgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.drop_table('otp')
    # ### end Alembic commands ###
