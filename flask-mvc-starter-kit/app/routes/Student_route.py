from flask import Blueprint
from app.controllers import Student_controller
prefix = 'Students'
route = Blueprint(prefix, __name__)

route.post('/student/create')(Student_controller.create)
route.delete('/student/delete')(Student_controller.delete)
route.put('/student/update')(Student_controller.update)
route.get('/student/showAll')(Student_controller.showAll)
route.get('/student/showID')(Student_controller.showID)
route.get('/student/showPage')(Student_controller.showPage)
route.get('/student/showPerUniversity')(Student_controller.showStudentsPerUniversity)
route.post('/student/login')(Student_controller.login)