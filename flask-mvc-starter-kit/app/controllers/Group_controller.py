from app.models.Group import Group
from app.models.admin import Admin
from app.models.Professor import Professor
from app.models.Course import Course
from flask import jsonify, request
def create():
    nrc = None
    group_number = None
    ced_professor = None
    code_course = None
    token = None
    if request.method == "POST":
        nrc = request.form.get("nrc")
        group_number = request.form.get("group_number")
        ced_professor = request.form.get("ced_professor")
        code_course = request.form.get("code_course")
        token = request.form.get("token")
    
    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    if not nrc:
        return jsonify({"Error": -1, "msg": "NRC is required"})
    if not group_number:
        return jsonify({"Error": -1, "msg": "Group number is required"})
    if not ced_professor:
        return jsonify({"Error": -1, "msg": "Professor cedula is required"})
    if not code_course:
        return jsonify({"Error": -1, "msg": "Code course is required"})
    
    admin = Admin.findJWT(token=token)
    
    if admin is None:
        return jsonify({"Error":-1, "msg":"Admin does not exist"})
    
    professor = Professor.query.get(ced_professor)
    
    if professor is None:
        return jsonify({"Error":-1, "msg":"Proffesor does not exist"})

    course = Course.query.get(code_course)
    
    if course is None:
        return jsonify({"Error":-1, "msg":"Course does not exist"})
    
    success, new_group = Group.create(nrc=nrc,group_number=group_number,
                                       ced_professor=ced_professor,code_course=code_course)
    if success:
        return jsonify({"Code": 1, "msg":"Group created"})
    else:
        return jsonify({"Error":-1, "msg":"Error creating the new group"})
        
    