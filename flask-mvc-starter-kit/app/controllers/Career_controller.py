from flask import request, jsonify
from app.models.Career import Career
from app.models.Department import Department
from app.models.admin import Admin

def create():
    name = None
    id_department = None
    token = None

    if request.method == "POST":
        name = request.form.get("name")
        id_department = request.form.get("id_department")
        token = request.form.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    if not name or not id_department:
        return jsonify({"Error": -1, "msg": "Name and Department ID are required"})
    
    admin = Admin.findJWT(token)
    
    if not admin:
        return jsonify({"Error": -1, "msg": "Unauthorized"})
    
    success, career = Career.create(name=name, id_department=id_department)
    if success:
        return jsonify({"code": 1, "msg": "Career created", "career": career.to_dict()})
    else:
        return jsonify({"Error": -1, "msg": "Error creating career"})

def update():
    id = None
    id_department = None
    name = None
    token = None

    if request.method == "PUT":
        id = request.form.get("id")
        id_department = request.form.get("id_department")
        name = request.form.get("name")
        token = request.form.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    if not id or not id_department:
        return jsonify({"Error": -1, "msg": "Career ID and Department ID are required"})

    success, career = Career.update(id=id, id_department=id_department, new_name=name)
    
    if success:
        return jsonify({"code": 1, "msg": "Career updated", "career": career.to_dict()})
    else:
        return jsonify({"Error": -1, "msg": career})

def delete():
    id = None
    id_department = None
    token = None

    if request.method == "DELETE":
        id = request.form.get("id")
        id_department = request.form.get("id_department")
        token = request.form.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    if not id or not id_department:
        return jsonify({"Error": -1, "msg": "Career ID and Department ID are required"})

    success, career = Career.delete(id=id, id_department=id_department)
    
    if success:
        return jsonify({"code": 1, "msg": "Career deleted"})
    else:
        return jsonify({"Error": -1, "msg": career})

def show_all():
    token = request.args.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})

    careers = Career.query.all()
    if len(careers) == 0:
        return jsonify({"Error": -1, "msg": "No careers found"})

    return jsonify({"code": 1, "msg": "Careers found", "careers": [career.to_dict() for career in careers]})

def show_by_id():
    id = request.args.get("id")
    token = request.args.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    if not id:
        return jsonify({"Error": -1, "msg": "Career ID is required"})

    career = Career.selectID(id)
    if career is None:
        return jsonify({"Error": -1, "msg": "Career not found"})
    else:
        return jsonify({"code": 1, "msg": career.to_dict()})
