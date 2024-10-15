from flask import Blueprint
from app.controllers import University_controller
prefix = 'Universitys'
route = Blueprint(prefix, __name__)

route.get('/Universitys')(University_controller.index)
route.get('/Universitys/create')(University_controller.create)
route.post('/Universitys')(University_controller.store)
route.get('/Universitys/<int:University_id>')(University_controller.show)
route.get('/Universitys/<int:University_id>/edit')(University_controller.edit)
route.post('/Universitys/<int:University_id>')(University_controller.update)
route.delete('/Universitys/<int:University_id>')(University_controller.delete)