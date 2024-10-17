from flask import Blueprint
from app.controllers import Professor_controller
prefix = 'Professors'
route = Blueprint(prefix, __name__)

route.get('/Professors')(Professor_controller.index)
route.get('/Professors/create')(Professor_controller.create)
route.post('/Professors')(Professor_controller.store)
route.get('/Professors/<int:Professor_id>')(Professor_controller.show)
route.get('/Professors/<int:Professor_id>/edit')(Professor_controller.edit)
route.post('/Professors/<int:Professor_id>')(Professor_controller.update)
route.delete('/Professors/<int:Professor_id>')(Professor_controller.delete)