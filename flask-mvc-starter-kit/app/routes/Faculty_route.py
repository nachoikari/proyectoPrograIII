from flask import Blueprint
from app.controllers import Faculty_controller
prefix = 'Facultys'
route = Blueprint(prefix, __name__)

route.post('/faculty/create')(Faculty_controller.create)
route.put('/faculty/update')(Faculty_controller.update)
route.delete('/faculty/delete')(Faculty_controller.delete)
route.get('/faculty/showAll')(Faculty_controller.showAll)
route.get('/faculty/showID')(Faculty_controller.showID)
route.get('/faculty/showPerUniversity')(Faculty_controller.showPerUniversity)
