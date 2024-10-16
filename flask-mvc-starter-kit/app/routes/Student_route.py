from flask import Blueprint
from app.controllers import Student_controller
prefix = 'Students'
route = Blueprint(prefix, __name__)

route.get('/Students')(Student_controller.index)
route.get('/Students/create')(Student_controller.create)
route.post('/Students')(Student_controller.store)
route.get('/Students/<int:Student_id>')(Student_controller.show)
route.get('/Students/<int:Student_id>/edit')(Student_controller.edit)
route.post('/Students/<int:Student_id>')(Student_controller.update)
route.delete('/Students/<int:Student_id>')(Student_controller.delete)