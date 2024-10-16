from flask import Blueprint
from app.controllers import admin_controller
prefix = 'admins'
route = Blueprint(prefix, __name__)

route.post('/admin/login')(admin_controller.login)
route.post('/admin/register')(admin_controller.register)
route.post('/admin/create')(admin_controller.create)
route.put('/admin/update')(admin_controller.update)
route.delete('/admin/delete')(admin_controller.delete)
route.get('/admin/showAll')(admin_controller.showAll)
route.get('/admin/showID')(admin_controller.showID)