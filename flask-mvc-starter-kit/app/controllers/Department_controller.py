from app.models.Department import Department
from flask import jsonify, request
from app.models.admin import Admin

def create():
    id_fac = None
    name = None
    token = None
    
    if request.method == "POST":
        id_fac = request.form.get("id_fac")
        name = request.form.get("name")
        token = request.form.get("token")

    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    if name is None or name == "":
        return jsonify({"Error": -1, "msg": "Name is required"})
    
    if id_fac is None or id_fac == "":
        return jsonify({"Error": -1, "msg": "Faculty id is required"})
    
    admin = Admin.findJWT(token=token)
    
    if admin is None:
        return jsonify({"Error": -1, "msg": "Admin doesn't exist"})
    
    success, result = Department.create(name=name, id_faculty=id_fac)
    
    if success:
        return jsonify({"code": 1, "msg": "Department created successfully", "department": result.to_dict()})
    else:
        return jsonify({"Error": -1, "msg": "Error creating department"})

def update():
    id = None
    name = None
    id_faculty = None
    token = None
    
    if request.method == "PUT":
        id = request.form.get("id")
        name = request.form.get("name")
        id_faculty = request.form.get("id_faculty")
        token = request.form.get("token")
    
    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    if id is None or id == "":
        return jsonify({"Error": -1, "msg": "Department ID is required"})
    
    admin = Admin.findJWT(token=token)
    
    if admin is None:
        return jsonify({"Error": -1, "msg": "Admin doesn't exist"})
    
    success, result = Department.update(id=id, new_name=name, new_idFac=id_faculty)
    
    if success:
        return jsonify({"code": 1, "msg": "Department updated successfully", "department": result.to_dict()})
    else:
        return jsonify({"Error": -1, "msg": "Error updating department"})

def delete():
    id = None
    token = None
    
    if request.method == "DELETE":
        id = request.form.get("id")
        token = request.form.get("token")
    
    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    if id is None or id == "":
        return jsonify({"Error": -1, "msg": "Department ID is required"})
    
    admin = Admin.findJWT(token=token)
    
    if admin is None:
        return jsonify({"Error": -1, "msg": "Admin doesn't exist"})
    
    success, result = Department.delete(id=id)
    
    if success:
        return jsonify({"code": 1, "msg": "Department deleted successfully"})
    else:
        return jsonify({"Error": -1, "msg": "Error deleting department"})

def showAll():
    token = request.args.get("token")

    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "Token is required"})

    admin = Admin.findJWT(token=token)

    if admin is None:
        return jsonify({"Error": -1, "msg": "Admin doesn't exist"})

    departments = Department.query.all()
    
    if len(departments) == 0:
        return jsonify({"Error": -1, "msg": "No departments found"})

    departments_list = [department.to_dict() for department in departments]
    
    return jsonify({"code": 1, "msg": "Departments found", "departments": departments_list})

def showID():
    id = request.args.get("id")
    token = request.args.get("token")

    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    if id is None or id == "":
        return jsonify({"Error": -1, "msg": "Department ID is required"})
    
    admin = Admin.findJWT(token=token)

    if admin is None:
        return jsonify({"Error": -1, "msg": "Admin doesn't exist"})

    department = Department.query.get(id)

    if department is None:
        return jsonify({"Error": -1, "msg": "Department not found"})
    
    return jsonify({"code": 1, "msg": "Department found", "department": department.to_dict()})
