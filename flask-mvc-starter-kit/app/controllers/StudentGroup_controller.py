from app.models.StudentGroup import Studentgroup
from app.models.Group import Group
from app.models.Student import Student
from app.models.admin import Admin
from app.models.Professor import Professor
from flask import jsonify, request

def register_student():
    token = None
    ced_student = None
    nrc_group = None
    grade = None
    if request.method == "POST":
        token = request.form.get("token")
        ced_student = request.form.get("ced_student")
        nrc_group = request.form.get("nrc_group")
        grade = request.form.get("grade")
    
    if token is None:
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    if ced_student is None:
        return jsonify({"Error": -1, "msg": "Student ced is required"})
    
    if nrc_group is None:
        return jsonify({"Error": -1, "msg": "NRC group is required"})
    
    if grade is None:
        return jsonify({"Error": -1, "msg": "Grade is required"})
    admin = Admin.findJWT(token=token)
    
    prof = Professor.findJWT(token=token)
    
    student = Student.findJWT(token=token)
    
    if admin is None and prof is None and student is None:
        return jsonify({"Error":-1, "msg":"You need to be a admin or professor to create groups"})
    
    if student is not None:
        grade = 0
    
    success, student_added = Studentgroup.registerStudent(ced_student=ced_student, nrc_group=nrc_group, grade=grade)

    if success:
        return jsonify({"Code": 1, "msg": "Student register in the group"})
    else:
        return jsonify({"Error": -1, "msg": "Error trying to register the student in the group"})

def delete():
    token = None
    nrc_group = None
    ced_student = None

    if request.method == "DELETE":
        token = request.form.get("token")
        ced_student = request.form.get("ced_student")
        nrc_group = request.form.get("nrc_group")

    # Verificar si el usuario está autorizado
    admin = Admin.findJWT(token=token)
    prof = Professor.findJWT(token=token)
    student = Student.findJWT(token=token)

    if admin is None and prof is None and student is None:
        return jsonify({"Error": -1, "msg": "You need to be an admin, professor, or student to drop a student"})

    # Llama al método delete del modelo StudentGroup
    success, message = Studentgroup.delete(ced_student=ced_student, nrc_group=nrc_group)

    if success:
        return jsonify({"code": 1, "msg": message})
    else:
        return jsonify({"Error": -1, "msg": message})

def update():
    token = None
    ced_student = None
    nrc_group = None
    grade = None
    new_nrc = None
    if request.method == "POST":
        token = request.form.get("token")
        ced_student = request.form.get("ced_student")
        nrc_group = request.form.get("nrc_group")
        grade = request.form.get("grade")
        new_nrc = request.form.get("new_nrc")
        