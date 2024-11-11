from flask import Blueprint
from app.controllers import Professor_controller
prefix = 'Professors'
route = Blueprint(prefix, __name__)

route.post('/professor/create')(Professor_controller.create)
route.delete('/professor/delete')(Professor_controller.delete)
route.put('/professor/update')(Professor_controller.update)
route.get('/professor/showAll')(Professor_controller.showAll)
route.get('/professor/showID')(Professor_controller.showID)
route.get('/professor/showPage')(Professor_controller.showPage)
route.post('/professor/login')(Professor_controller.login)