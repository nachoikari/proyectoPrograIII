from flask import Blueprint
from app.controllers import Student_controller
prefix = 'Students'
route = Blueprint(prefix, __name__)

route.post('/student/create')(Student_controller.create)
route.delete('/student/delete')(Student_controller.delete)
route.put('/student/update')(Student_controller.update)
route.get('/student/showAll')(Student_controller.showAll)
route.get('/student/showID')(Student_controller.showID)
route.post('/student/login')(Student_controller.login)