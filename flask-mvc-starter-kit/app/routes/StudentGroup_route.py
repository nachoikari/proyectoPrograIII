from flask import Blueprint
from app.controllers import StudentGroup_controller
prefix = 'StudentGroups'
route = Blueprint(prefix, __name__)

route.get('/StudentGroups')(StudentGroup_controller.index)
route.get('/StudentGroups/create')(StudentGroup_controller.create)
route.post('/StudentGroups')(StudentGroup_controller.store)
route.get('/StudentGroups/<int:StudentGroup_id>')(StudentGroup_controller.show)
route.get('/StudentGroups/<int:StudentGroup_id>/edit')(StudentGroup_controller.edit)
route.post('/StudentGroups/<int:StudentGroup_id>')(StudentGroup_controller.update)
route.delete('/StudentGroups/<int:StudentGroup_id>')(StudentGroup_controller.delete)