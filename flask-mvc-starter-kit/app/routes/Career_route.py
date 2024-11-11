from flask import Blueprint
from app.controllers import Career_controller

prefix = 'Careers'
route = Blueprint(prefix, __name__)

route.post('/career/create')(Career_controller.create)
route.put('/career/update')(Career_controller.update)
route.delete('/career/delete')(Career_controller.delete)
route.get('/career/showAll')(Career_controller.show_all)
route.get('/career/showID')(Career_controller.show_by_id)
route.get('/career/showPage')(Career_controller.showPage)