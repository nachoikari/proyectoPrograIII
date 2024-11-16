from flask import Blueprint
from app.controllers import Group_controller
prefix = 'Groups'
route = Blueprint(prefix, __name__)

route.post('/group/create/')(Group_controller.create)
route.put('/group/update')(Group_controller.update)
route.delete('/group/delete')(Group_controller.delete)
route.get('/group/find_nrc')(Group_controller.showNRC)
route.get('/group/showAll')(Group_controller.show_groups)
route.get('/group/showProfGroups')(Group_controller.professorGroups)