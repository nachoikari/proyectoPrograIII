from flask import Blueprint
from app.controllers import Career_controller
prefix = 'Careers'
route = Blueprint(prefix, __name__)

route.get('/Careers')(Career_controller.index)
route.get('/Careers/create')(Career_controller.create)
route.post('/Careers')(Career_controller.store)
route.get('/Careers/<int:Career_id>')(Career_controller.show)
route.get('/Careers/<int:Career_id>/edit')(Career_controller.edit)
route.post('/Careers/<int:Career_id>')(Career_controller.update)
route.delete('/Careers/<int:Career_id>')(Career_controller.delete)