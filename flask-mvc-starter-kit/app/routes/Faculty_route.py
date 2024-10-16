from flask import Blueprint
from app.controllers import Faculty_controller
prefix = 'Facultys'
route = Blueprint(prefix, __name__)

route.get('/Facultys')(Faculty_controller.index)
route.get('/Facultys/create')(Faculty_controller.create)
route.post('/Facultys')(Faculty_controller.store)
route.get('/Facultys/<int:Faculty_id>')(Faculty_controller.show)
route.get('/Facultys/<int:Faculty_id>/edit')(Faculty_controller.edit)
route.post('/Facultys/<int:Faculty_id>')(Faculty_controller.update)
route.delete('/Facultys/<int:Faculty_id>')(Faculty_controller.delete)