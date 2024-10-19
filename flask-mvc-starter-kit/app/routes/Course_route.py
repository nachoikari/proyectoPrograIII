from flask import Blueprint
from app.controllers import Course_controller
prefix = 'Courses'
route = Blueprint(prefix, __name__)

route.get('/Courses')(Course_controller.index)
route.get('/Courses/create')(Course_controller.create)
route.post('/Courses')(Course_controller.store)
route.get('/Courses/<int:Course_id>')(Course_controller.show)
route.get('/Courses/<int:Course_id>/edit')(Course_controller.edit)
route.post('/Courses/<int:Course_id>')(Course_controller.update)
route.delete('/Courses/<int:Course_id>')(Course_controller.delete)