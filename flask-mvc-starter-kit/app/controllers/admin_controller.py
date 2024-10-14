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
def create():
    print("hola")