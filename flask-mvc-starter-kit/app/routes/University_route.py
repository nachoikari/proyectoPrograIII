from flask import Blueprint
from app.controllers import University_controller
prefix = 'Universitys'
route = Blueprint(prefix, __name__)

route.post('/university/create')(University_controller.create)
route.delete('/university/delete')(University_controller.delete)
route.put('/university/update')(University_controller.update)
route.get('/university/showAll')(University_controller.showAll)
route.get('/university/showID')(University_controller.showID)