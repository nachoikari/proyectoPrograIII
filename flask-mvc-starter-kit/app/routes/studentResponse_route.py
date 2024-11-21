from flask import Blueprint
from app.controllers import studentResponse_controller
prefix = 'studentResponses'
route = Blueprint(prefix, __name__)

route.post('/student_response/create')(studentResponse_controller.create)
route.put('/student_response/manualEvaluate')(studentResponse_controller.manualEvaluate)
route.delete('/student_response/delete')(studentResponse_controller.delete)
route.get('/student_response/showResponses')(studentResponse_controller.showPerGroup)
route.get('/student_response/showPerAssignment')(studentResponse_controller.showPerAssignment)
route.put('/student_response/iaEvaluate')(studentResponse_controller.iaEvaluate)