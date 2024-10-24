from flask import Blueprint
from app.controllers import Course_controller
prefix = 'Courses'
route = Blueprint(prefix, __name__)

route.post('/course/create')(Course_controller.create)
route.delete('/course/delete')(Course_controller.delete)
route.put('/course/update')(Course_controller.update)
route.get('/course/showCode')(Course_controller.showByCode)
route.get('/course/showAll')(Course_controller.showAll)