from app.models.Student import Student
from flask import jsonify, request
from app.models.admin import Admin
from app.models.Career import Career

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
        return jsonify({"Error":-1, "msg":"Career not found"})
    
    success, new_student = Student.create(
        ced=ced,
        name=name,
        email=email,
        password=password,
        id_career=idCareer,
        phone_number=phone_number
    )
    
    if success:
        return jsonify({"Code": 1, "msg":"Student created"})
    else:
        return jsonify({"Error":-1, "msg":"Error creating Student"})
    
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
    success, result = Student.delete(ced=ced)

    if success:
        return jsonify({"Code": 1, "msg":"Student deleted"})
    else:
        return jsonify({"Error":-1, "msg":"Error deleting Student"})

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
        return jsonify({"Error": -1, "msg": "Ced of the Student is necessary"})
    
    professor_update = Student.selectID(ced)
    
    if professor_update is None:
        return jsonify({"Error": -1, "msg": "Student doesn't exist"})
    
    name = None if name is None or name.strip() == "" else name
    password = None if password is None or password.strip() == "" else password
    email = None if email is None or email.strip() == "" else email
    phone_number = None if phone_number is None or phone_number.strip() == "" else phone_number

    success, result = Student.update(ced=ced, new_name=name, new_email=email, new_password=password, new_phone_number=phone_number)
    
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
    
    students = Student.query.all()
    
    if len(students) == 0:
        return jsonify({"Error": -1, "msg":"Students dont exist "})
    
    
    students_list = [professor.to_dict1() for professor in students]

    return jsonify({"code": 1, "msg":"Students find", "students":students_list})

def showID():
    if request.method == "GET":
        ced = request.args.get("ced")
        token = request.args.get("token")

    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "El token es necesario"})
    
    if ced is None or ced == "":
        return jsonify({"Error": -1, "msg": "La cédula es necesaria"})
    
    student = Student.selectID(ced)
    
    if student is None:
        return jsonify({"Error": -1, "msg": "Admin no encontrado"})
    else:
        return jsonify({"code": 1, "msg": student.to_dict()})
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
        Student_paged = Student.query.filter_by(id_career = id_career).paginate(page=page, per_page=per_page, error_out=False)
        if Student_paged.items:
            students = [student.to_dict1() for student in Student_paged.items]
            return jsonify({
                "code": 1,
                "msg": "Professors found",
                "Student": students,
                "total_pages": Student_paged.pages,
                "current_page": Student_paged.page
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
    
    student = Student.query.get(ced)
    if student is None:
        return jsonify({"Error": -1, "msg":"Access denied"})
    
    if password != student.password:
        return jsonify({"Error":-1,"msg":"Incorrect password"})
    
    return jsonify({"code": 1, "msg": "Authorized access", "jwt": student.token})