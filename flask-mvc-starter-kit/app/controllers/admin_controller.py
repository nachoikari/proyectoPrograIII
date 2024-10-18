import logging
from app.models.admin import Admin
from flask import request, jsonify

# Configura el logger
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

def login():
    ced = None
    password = None
    if request.method == "POST":
        ced = request.form.get("ced")
        password = request.form.get("password")

        logger.info(f"ced: {ced}")
        logger.info(f"password: {password}")

    if ced is None or ced == "" or password is None or password == "":
        return '{"Error":-1, "msg":"Error al iniciar sesión"}'

    admin = Admin.selectID(ced)

    if admin is None:
        return '{"error":-1,"msg":"Acceso no autorizado"}'

    if admin.password == password:
        return jsonify({"code": 1, "msg": "Acceso autorizado", "jwt": admin.token})

    return '{"Error":-1,"msg":"Acceso no autorizado"}'

def register():
    if request.method == "POST":
        ced = request.form.get("ced")
        password = request.form.get("password")
        email = request.form.get("email")
        name = request.form.get("name")

    if not ced or not password or not email or not name:
        return jsonify({"Error": -1, "msg": "Todos los datos son necesarios"})

    success, new_admin = Admin.create(ced=ced, password=password, name=name, email=email)

    if success:
        return jsonify({"code": 1, "msg": "Admin creado exitosamente", "admin": new_admin.ced})
    else:
        return jsonify({"Error": -1, "msg": "Error al crear admin"})

def create():
    if request.method == "POST":
        ced = request.form.get("ced")
        password = request.form.get("password")
        email = request.form.get("email")
        name = request.form.get("name")
        token = request.form.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token es necesario"})

    admin = Admin.findJWT(token=token)

    if admin is None:
        return jsonify({"Error": -1, "msg": "Admin no encontrado o token inválido"})

    if not ced or not password or not email or not name:
        return jsonify({"Error": -1, "msg": "Todos los datos son necesarios"})

    success, new_admin = Admin.create(ced=ced, password=password, name=name, email=email)

    if success:
        return jsonify({"code": 1, "msg": "Admin creado exitosamente", "admin": new_admin.ced})
    else:
        return jsonify({"Error": -1, "msg": "Error al crear admin"})
def update():
    if request.method == "PUT":
        ced = request.form.get("ced") 
        new_name = request.form.get("name") 
        new_email = request.form.get("email") 
        token = request.form.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Necesita un token para realizar el peticion"})
    
    if not ced:
        return jsonify({"Error": -1, "msg": "El ID del administrador es necesario"})
  
    admin_update = Admin.selectID(ced)
   
    if admin_update is None:
        return jsonify({"Error": -1, "msg": "Administrador no encontrado"})
  
    success, result = Admin.update(ced=ced, new_name=new_name, new_email=new_email)
    if success:
        return jsonify({"code": 1, "msg": "Administrador actualizado exitosamente", "admin": result.ced})
    else:
        return jsonify({"Error": -1, "msg": result})
def delete():
    if request.method == "DELETE":
        ced=request.form.get("ced")
        jwt=request.form.get("jwt")
    
    if not jwt:
        return jsonify({"Error": -1, "msg": "Necesario el token "})
    
    if not ced:
        return jsonify({"Error": -1, "msg": "Ncesaria la cedula del que quiere eliminar "})
    
    success, result = Admin.delete(ced=ced)
    
    if success:
        return jsonify({"code": 1, "msg": "Administrador eliminado exitosamente"})
    else:
        return jsonify({"Error": -1, "msg": result})
def showAll():
    # Obtener el token desde los parámetros de la URL
    token = request.args.get("token")

    # Verificar si el token es válido
    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "El token es necesario"})

    # Obtener todos los administradores
    admins = Admin.query.all()

    if len(admins) == 0:
        return jsonify({"Error": -1, "msg": "No se encontraron admins"})

    # Convertir administradores a diccionario
    admin_list = [admin.to_dict() for admin in admins]
    return jsonify({"code": 1, "msg": "Admins encontrados", "admins": admin_list})
def showID():
    # Obtener los parámetros desde la URL
    ced = request.args.get("ced")
    token = request.args.get("token")

    # Verificar si el token y la cédula son válidos
    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "El token es necesario"})
    if ced is None or ced == "":
        return jsonify({"Error": -1, "msg": "La cédula es necesaria"})

    # Buscar el administrador por cédula
    admin = Admin.selectID(ced)
    if admin is None:
        return jsonify({"Error": -1, "msg": "Admin no encontrado"})
    else:
        return jsonify({"code": 1, "msg": admin.to_dict()})