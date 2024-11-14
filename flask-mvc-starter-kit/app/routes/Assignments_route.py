from flask import Blueprint
from app.controllers import Assignments_controller
prefix = 'Assignmentss'
route = Blueprint(prefix, __name__)

route.post('/assignments/create')(Assignments_controller.create)
route.delete('/assignments/delete')(Assignments_controller.delete)