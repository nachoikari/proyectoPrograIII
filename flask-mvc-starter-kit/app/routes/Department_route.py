from flask import Blueprint
from app.controllers import Department_controller
prefix = 'Departments'
route = Blueprint(prefix, __name__)

route.post('/department/create')(Department_controller.create)
route.delete('/department/delete')(Department_controller.delete)
route.put('/department/update')(Department_controller.update)
route.get('/department/showAll')(Department_controller.showAll)
route.get('/department/showID')(Department_controller.showID)