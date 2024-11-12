from flask import Blueprint
from app.controllers import StudentGroup_controller
prefix = 'StudentGroups'
route = Blueprint(prefix, __name__)

route.post('/enroll/register')(StudentGroup_controller.register_student)
route.delete('/enroll/delete')(StudentGroup_controller.delete)
route.put('/enroll/update')(StudentGroup_controller.update)
route.get('/enroll/showPerGroup')(StudentGroup_controller.showPerGroup)