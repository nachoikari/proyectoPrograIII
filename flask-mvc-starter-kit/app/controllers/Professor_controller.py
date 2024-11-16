from app.models.Professor import Professor
from flask import jsonify, request
from app.models.admin import Admin
from app.models.Career import Career
from app.models.Department import Department
from app.models.Faculty import Faculty
from app.models.University import University
def create():
    ced = None
    name = None
    password = None
    email = None
    idCareer = None
    token = None
    phone_number = None
    if request.method == "POST":
        ced = request.form.get("ced")
        name = request.form.get("name")
        password = request.form.get("password")
        email = request.form.get("email")
        token = request.form.get("token")
        idCareer = request.form.get("id_career")
        phone_number = request.form.get("phone_number")
    
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
    
    if idCareer == None or idCareer == "":
        return jsonify({"Error": -1, "msg": "Id Career is required"})
    if phone_number == None or phone_number == "":
        return jsonify({"Error": -1, "msg": "Phone number is required"})
    
    admin = Admin.findJWT(token=token)
    
    if admin is None:
        return jsonify({"Error":-1, "msg":"Admin doesnt exist"})
    
    career = Career.query.get(idCareer)
    
    if career is None:
        return jsonify({"Error":-1, "msg":"Facultad no encontrado en base de datos"})
    
    success, new_professor = Professor.create(
        ced=ced,
        name=name,
        email=email,
        password=password,
        id_career=idCareer,
        phone_number=phone_number
    )
    
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
    token = None
    phone_number = None
    
    if request.method == "PUT":
        ced = request.form.get("ced")
        name = request.form.get("name")
        password = request.form.get("password")
        email = request.form.get("email")
        token = request.form.get("token")
        phone_number = request.form.get("phone_number")

    if not token:
        return jsonify({"Error": -1, "msg": "You need a token"})
    
    if not ced:
        return jsonify({"Error": -1, "msg": "Ced of the professor is necessary"})
    
    professor_update = Professor.selectID(ced)
    
    if professor_update is None:
        return jsonify({"Error": -1, "msg": "Professor doesn't exist"})
    
    name = None if name is None or name.strip() == "" else name
    password = None if password is None or password.strip() == "" else password
    email = None if email is None or email.strip() == "" else email
    phone_number = None if phone_number is None or phone_number.strip() == "" else phone_number

    success, result = Professor.update(ced=ced, new_name=name, new_email=email, new_password=password, new_phone_number=phone_number)
    
    if success:
        return jsonify({"code": 1, "msg": "Professor updated", "Professor": result.ced})
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
        return jsonify({"Error": -1, "msg":"Professors dont exist "})
    
    
    professors_list = [professor.to_dict1() for professor in professors]

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
def showPage():
    token = None
    id_career = None
    if request.method == "GET":
        token = request.args.get("token")
        id_career = request.args.get("career_id")
    if token is None:
        return jsonify({
            "Error":-1,
            "msg": "Token is required"
        })
    if id_career is None:
        return jsonify({
            "Error":-1,
            "msg": "Career id is required"
        })
    try:
        page = int(request.args.get('page', 1))  # Página actual, por defecto la 1
        per_page = int(request.args.get('per_page', 10))  # Elementos por página, por defecto 10
        professors_paged = Professor.query.filter_by(id_career = id_career).paginate(page=page, per_page=per_page, error_out=False)
        if professors_paged.items:
            professors = [professor.to_dict1() for professor in professors_paged.items]
            return jsonify({
                "code": 1,
                "msg": "Professors found",
                "professors": professors,
                "total_pages": professors_paged.pages,
                "current_page": professors_paged.page
            })
        else:
            return jsonify({"Error": -1, "msg": "No professors found"})
    except Exception as e:
        return jsonify({"Error": -1, "msg": f"An error occurred: {e}"})

def showPerUniversity():
    token = None
    university_id = None
    if request.method == "GET":
        token = request.args.get("token")
        university_id = request.args.get("university_id")
    
    if token is None:
        return jsonify({
            "Error": -1,
            "msg": "Token is required"
        })
    if university_id is None:
        return jsonify({
            "Error": -1,
            "msg": "University ID is required"
        })
    
    try:
        page = int(request.args.get('page', 1))  # Página actual, por defecto la 1
        per_page = int(request.args.get('per_page', 10))  # Elementos por página, por defecto 10

        # Consulta con JOIN para obtener los profesores de una universidad específica
        professors_paged = (
            Professor.query
            .join(Career, Professor.id_career == Career.id)
            .join(Department, Career.id_department == Department.id)
            .join(Faculty, Department.id_Faculty == Faculty.id)
            .join(University, Faculty.id_university == University.id)
            .filter(University.id == university_id)
            .paginate(page=page, per_page=per_page, error_out=False)
        )

        if professors_paged.items:
            # Convertir cada profesor a un diccionario (se supone que el modelo Professor tiene un método `to_dict1`)
            professors = [professor.to_dict1() for professor in professors_paged.items]
            return jsonify({
                "code": 1,
                "msg": "Professors found",
                "professors": professors,
                "total_pages": professors_paged.pages,
                "current_page": professors_paged.page
            })
        else:
            return jsonify({"Error": -1, "msg": "No professors found"})

    except Exception as e:
        return jsonify({"Error": -1, "msg": f"An error occurred: {e}"})
def login():
    ced = None
    password = None

    if request.method == "POST":
        ced = request.form.get("ced")
        password = request.form.get("password")
    
    if ced is None or ced == "" or password is None or password == "":
        return '{"Error":-1, "msg":"Check password or Ced"}'
    
    professor = Professor.query.get(ced)
    if professor is None:
        return jsonify({"Error": -1, "msg":"Access denied"})
    
    if password != professor.password:
        return jsonify({"Error":-1,"msg":"Incorrect password"})
    
    return jsonify({"code": 1, "msg": "Authorized access", "jwt": professor.to_dict()})