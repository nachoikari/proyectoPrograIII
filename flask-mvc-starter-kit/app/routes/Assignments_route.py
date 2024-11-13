from flask import Blueprint
from app.controllers import Assignments_controller
prefix = 'Assignmentss'
route = Blueprint(prefix, __name__)

route.get('/Assignmentss')(Assignments_controller.index)
route.get('/Assignmentss/create')(Assignments_controller.create)
route.post('/Assignmentss')(Assignments_controller.store)
route.get('/Assignmentss/<int:Assignments_id>')(Assignments_controller.show)
route.get('/Assignmentss/<int:Assignments_id>/edit')(Assignments_controller.edit)
route.post('/Assignmentss/<int:Assignments_id>')(Assignments_controller.update)
route.delete('/Assignmentss/<int:Assignments_id>')(Assignments_controller.delete)