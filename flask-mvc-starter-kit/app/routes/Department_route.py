from flask import Blueprint
from app.controllers import Department_controller
prefix = 'Departments'
route = Blueprint(prefix, __name__)

route.get('/Departments')(Department_controller.index)
route.get('/Departments/create')(Department_controller.create)
route.post('/Departments')(Department_controller.store)
route.get('/Departments/<int:Department_id>')(Department_controller.show)
route.get('/Departments/<int:Department_id>/edit')(Department_controller.edit)
route.post('/Departments/<int:Department_id>')(Department_controller.update)
route.delete('/Departments/<int:Department_id>')(Department_controller.delete)