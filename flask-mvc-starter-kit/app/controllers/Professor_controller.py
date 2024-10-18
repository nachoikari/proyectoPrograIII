from app.models.Professor import Professor
from flask import jsonify, request
from app.models.admin import Admin
from app.models.Faculty import Faculty

def create():
    ced = None
    name = None
    password = None
    email = None
    idFaculty = None
    token = None
    
    if request.method == "POST":
        ced = request.form.get("ced")
        name = request.form.get("name")
        password = request.form.get("password")
        email = request.form.get("email")
        token = request.form.get("token")
        idFaculty = request.form.get("id_faculty")
    
    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    if name == None or name == "":
        return jsonify({"Error": -1, "msg": "Name is required"})
    
    if ced == None or ced == "":
        return jsonify({"Error": -1, "msg": "Cedula is required"})
    
    if password == None or password == "":
        return jsonify({"Error": -1, "msg": "Password is required"})
    
    if email == None or email == "":
        return jsonify({"Error": -1, "msg": "Email is required"})
    
    if idFaculty == None or idFaculty == "":
        return jsonify({"Error": -1, "msg": "Id faculty is required"})
    
    admin = Admin.findJWT(token=token)
    
    if admin is None:
        return jsonify({"Error":-1, "msg":"Admin doesnt exist"})
    
    fac = Faculty.query.get(idFaculty)
    
    if fac is None:
        return jsonify({"Error":-1, "msg":"Facultad no encontrado en base de datos"})
    
    success, new_professor = Professor.create(ced=ced,name=name,email=email,password=password,id_faculty=idFaculty)
    
    if success:
        return jsonify({"Code": 1, "msg":"Professor created"})
    else:
        return jsonify({"Error":-1, "msg":"Error creating professor"})
    
def delete():
    ced = None
    token = None
    
    if request.method == "DELETE":
        ced = request.form.get("ced")
        token = request.form.get("token")
    
    if ced == None or ced == "":
        return jsonify({"Error": -1, "msg": "Cedula is required"})
    
    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    admin = Admin.findJWT(token=token)
    
    if admin is None:
        return jsonify({"Error":-1, "msg":"Admin doesnt exist"})
    success, result = Professor.delete(ced=ced)

    if success:
        return jsonify({"Code": 1, "msg":"Professor deleted"})
    else:
        return jsonify({"Error":-1, "msg":"Error deleting professor"})

def update():
    ced = None
    name = None
    password = None
    email = None
    idFaculty = None
    token = None
    new_ced = None
    
    if request.method == "PUT":
        ced = request.form.get("ced")
        name = request.form.get("name")
        password = request.form.get("password")
        email = request.form.get("email")
        token = request.form.get("token")
        idFaculty = request.form.get("idFaculty")
        new_ced = request.form.get("new_ced")
    
    if not token:
        return jsonify({"Error": -1, "msg": "You need a token"})
    
    if not ced:
        return jsonify({"Error": -1, "msg": "Ced of the student is necesary"})
    
    professor_update = Professor.selectID(ced)
    
    if professor_update == None:
        return jsonify({"Error": -1, "msg": "Student doesnt exist"})
    
    ced = None if ced is None or ced.strip() == "" else ced
    name = None if name is None or name.strip() == "" else name
    password = None if password is None or password.strip() == "" else password
    email = None if email is None or email.strip() == "" else email
    token = None if token is None or token.strip() == "" else token
    idFaculty = None if idFaculty is None or idFaculty.strip() == "" else idFaculty
    new_ced = None if new_ced is None or new_ced.strip() == "" else new_ced
    
    success, result = Professor.update(ced=ced,new_name=name,new_email=email,new_password=password,new_faculty=idFaculty,new_ced=new_ced)
    
    if success:
        return jsonify({"code": 1, "msg": "Student updated", "Student": result.ced})
    else:
        return jsonify({"Error": -1, "msg": result})
    
def showAll():
    token = None
    
    if request.method == "GET":
        token = request.args.get("token")
    
    if token == None or token=="":
        return jsonify({"Error": -1, "msg": "Token is necesary"})
    
    professors = Professor.query.all()
    
    if len(professors) == 0:
        return jsonify({"Error": -1, "msg":"Students dont exist "})
    
    
    professors_list = [professor.to_dict() for professor in professors]

    return jsonify({"code": 1, "msg":"professors find", "professors":professors_list})

def showID():
    if request.method == "GET":
        ced = request.args.get("ced")
        token = request.args.get("token")

    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "El token es necesario"})
    
    if ced is None or ced == "":
        return jsonify({"Error": -1, "msg": "La cédula es necesaria"})
    
    professor = Professor.selectID(ced)
    
    if professor is None:
        return jsonify({"Error": -1, "msg": "Admin no encontrado"})
    else:
        return jsonify({"code": 1, "msg": professor.to_dict()})
    
def login():
    ced = None
    password = None
    id_faculty = None

    if request.method == "POST":
        ced = request.form.get("ced")
        password = request.form.get("password")
        id_faculty = request.form.get("id_faculty")
    
    if ced is None or ced == "" or password is None or password == "":
        return '{"Error":-1, "msg":"Check password or Ced"}'
    
    professor = Professor.query.filter_by(id_faculty=id_faculty,ced=ced).first()

    if professor is None:
        return jsonify({"Eror": -1, "msg":"Access denied"})
    
    if password != professor.password:
        return jsonify({"Error":-1,"msg":"Incorrect password"})
    
    return jsonify({"code": 1, "msg": "Authorized access", "jwt": professor.token})