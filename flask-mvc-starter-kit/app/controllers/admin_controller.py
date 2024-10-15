import logging
from app.models.admin import Admin
from flask import request, jsonify

# Configura el logger
logging.basicConfig(level=logging.INFO)  # Puedes cambiar el nivel de logging si es necesario
logger = logging.getLogger(__name__)

def login():
    ced = None
    password = None
    if request.method == "POST":
        ced = request.form.get("ced")
        password = request.form.get("password")
        
        # En lugar de print, utiliza logger
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
    ced = request.form.get("ced")
    password = request.form.get("password")
    email = request.form.get("email")
    name = request.form.get("name")
    token = request.form.get("token")

    # Validar que el token no esté vacío
    if not token:
        return jsonify({"Error": -1, "msg": "Token es necesario"})

    # Buscar el admin con el token proporcionado
    admin = Admin.findJWT(token=token)

    if admin is None:
        return jsonify({"Error": -1, "msg": "Admin no encontrado o token inválido"})

    # Validar que los datos del formulario no estén vacíos
    if not ced or not password or not email or not name:
        return jsonify({"Error": -1, "msg": "Todos los datos son necesarios"})

    # Intentar crear un nuevo admin
    success, new_admin = Admin.create(ced=ced, password=password, name=name, email=email)

    if success:
        return jsonify({"code": 1, "msg": "Admin creado exitosamente", "admin": new_admin.ced})
    else:
        return jsonify({"Error": -1, "msg": "Error al crear admin"})
def update():
    ced = request.form.get("ced")  # Obtener el ID del administrador a actualizar
    new_name = request.form.get("name")  # Obtener el nuevo nombre
    new_email = request.form.get("email")  # Obtener el nuevo correo electrónico
    # Verificar si se proporciona el ced y si no está vacío
    if not ced:
        return jsonify({"Error": -1, "msg": "El ID del administrador es necesario"})
    # Seleccionar el administrador existente
    admin_update = Admin.selectID(ced)
    # Verificar si el administrador existe
    if admin_update is None:
        return jsonify({"Error": -1, "msg": "Administrador no encontrado"})
    # Intentar actualizar el administrador
    success, result = Admin.update(ced=ced, new_name=new_name, new_email=new_email)
    if success:
        return jsonify({"code": 1, "msg": "Administrador actualizado exitosamente", "admin": result.ced})
    else:
        return jsonify({"Error": -1, "msg": result})
def delete():
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