from app.models.Faculty import Faculty
from app.models.University import University
from flask import request, jsonify

def create():
    name=None
    id_university = None
    token = None
    if request.method == "POST":
        name = request.form.get("name")
        id_university = request.form.get("id_University")
        token = request.form.get("token")
    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    if name == None or name == "":
        return jsonify({"Error": -1, "msg": "Name is required"})
    university=University.selectID(id_university)
    if university == None:
        return jsonify({"Error": -1, "msg": "University doesnt exist"})
    
    success, new_faculty = Faculty.create(name=name,id_universidad=id_university)
    if success:
        return jsonify({"Code": 1, "msg": "Faculty created"})
    else:
        return jsonify({"Error": -1, "msg": "Creating faculty"})
def update():
    id = None
    name = None
    id_university = None
    token = None  
    
    if request.method == "PUT":
        id = request.form.get("id")
        name = request.form.get("name")
        id_university = request.form.get("id_University")
        token = request.form.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token necesario para la petición"})
    if not id:
        return jsonify({"Error": -1, "msg": "El ID de la facultad es necesario"})

    faculty_update = Faculty.selectID(id)
    if faculty_update is None:
        return jsonify({"Error": -1, "msg": "Facultad no encontrada"})

    if name == "":
        name = None
    if id_university == "" or id_university is None:
        id_university = None

    success, result = Faculty.update(id_faculty=id, new_name=name, id_university=id_university)
    
    if success:
        return jsonify({"code": 1, "msg": "Facultad actualizada", "faculty": result.name})
    else:
        return jsonify({"Error": -1, "msg": result})
def delete():
    token = None
    id = None
    if request.method == "DELETE":
        token = request.form.get("token")
        id = request.form.get("id")
    if id == None or id == "":
        return jsonify({"Error":-1, "msg":"El id es necesario"})
    if token == None or token =="":
        return jsonify({"Error":-1, "msg":"El token es necesario"})
    success, result = Faculty.delete(id=id)
    if success:
        return jsonify({"Code":1,"msg":"Facultad eliminada con exito"})
    else:
        return jsonify({"Error":1, "msg": "Error al elimininar la facultad"})
def showID():
    if request.method == "GET":
        id = request.args.get("id")  # Usar args para GET
        token = request.args.get("token")
    
    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    if not id:
        return jsonify({"Error": -1, "msg": "Faculty ID is required"})

    faculty = Faculty.query.get(id)
    if faculty is None:
        return jsonify({"Error": -1, "msg": "Faculty not found"})
    else:
        return jsonify({"code": 1, "msg": "Faculty found", "faculty": faculty.to_dict()})

def showAll():
    if request.method == "GET":
        token = request.args.get("token")
    
    # Validaciones
    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})

    # Obtener todas las facultades
    faculties = Faculty.query.all()
    faculties_list = [faculty.to_dict() for faculty in faculties]
    return jsonify({"Code": 1, "msg": "Faculties found", "Faculties": faculties_list})

def showPerUniversity():
    if request.method == "GET":
        id_university = request.args.get("id_university")
        token = request.args.get("token")

    # Validaciones
    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    if not id_university:
        return jsonify({"Error": -1, "msg": "University ID is required"})

    # Obtener facultades por universidad
    faculties = Faculty.query.filter_by(id_university=id_university).all()
    faculties_list = [faculty.to_dict() for faculty in faculties]
    return jsonify({"Code": 1, "msg": "Faculties found", "Faculties": faculties_list})