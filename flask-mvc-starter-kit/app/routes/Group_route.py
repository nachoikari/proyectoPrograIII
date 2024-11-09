from flask import Blueprint
from app.controllers import Group_controller
prefix = 'Groups'
route = Blueprint(prefix, __name__)

route.post('/group/create/')