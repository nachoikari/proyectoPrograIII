from flask import Blueprint
from app.controllers import admin_controller
prefix = 'admins'
route = Blueprint(prefix, __name__)

route.post('/admin/login')(admin_controller.login)
route.post('/admin/create')(admin_controller.create)



#route.get('/admins')(admin_controller.index)
#route.get('/admins/create')(admin_controller.create)
#route.post('/admins')(admin_controller.store)
#route.get('/admins/<int:admin_id>')(admin_controller.show)
#route.get('/admins/<int:admin_id>/edit')(admin_controller.edit)
#route.post('/admins/<int:admin_id>')(admin_controller.update)
#route.delete('/admins/<int:admin_id>')(admin_controller.delete)