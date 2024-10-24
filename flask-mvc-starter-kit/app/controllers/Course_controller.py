from app.models.Course import Course
from app.models.admin import Admin
from app.models.Career import Career
from flask import jsonify, request

def create():
    name = None
    code = None
    id_career = None
    token = None
    if request.method == "POST":
        name = request.form.get("name")
        code = request.form.get("code")
        id_career = request.form.get("career")
        token = request.form.get("token")
    id_career = id_career.replace(" ","")
    code = code.replace(" ","")
    admin = Admin.findJWT(token = token)
    if admin is None:
        return jsonify ({"Code": -1, "msg": "Admin doesnt exist"})
    if code == None or code == "":
        return jsonify ({"Code": -1, "msg": "Code is required"})
    if name == "":
        return jsonify ({"Code": -1, "msg": "Name is required"})
    success, course = Course.create(code = code,name = name, id_career=id_career)
    if success:
        return jsonify ({"Code": 1, "msg": "Course created"})
    else:
        return jsonify ({"Error": -1, "msg": "Error creating course"})

def delete():
    code = None
    token = None
    if request.method == "DELETE":
        code = request.form.get("code")
        token = request.form.get("token")
    admin = Admin.findJWT(token=token)
    if admin is None:
        return jsonify ({"Error": -1, "msg": "You need be a admin"})
    course = Course.query.get(code)
    if course is None:
        return jsonify ({"Error": -1, "msg": "Course doesnt exist"})
    if Course.delete(code) is False:
        return jsonify ({"Error": -1, "msg": "Error deleting course"})
    else:
        return jsonify ({"Code": 1, "msg": "Course deleted"})

def update():
    name = None
    code = None
    id_career = None
    token = None

    if request.method == "PUT":
        name = request.form.get("name")
        code = request.form.get("code")
        id_career = request.form.get("career")
        token = request.form.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    admin = Admin.findJWT(token=token)
    if admin is None:
        return jsonify({"Error": -1, "msg": "You need to be an admin"})

    if not code:
        return jsonify({"Error": -1, "msg": "Course code is required"})

    course = Course.select_by_code(code=code)
    if course is None:
        return jsonify({"Error": -1, "msg": "Course not found"})

    success, updated_course = Course.update(code=code, new_name=name, new_id_career=id_career)
    if success:
        return jsonify({"Code": 1, "msg": "Course updated successfully", "course": updated_course.to_dict()})
    else:
        return jsonify({"Error": -1, "msg": "Error updating course"})

def showByCode():
    code = request.args.get("code")
    token = request.args.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})

    admin = Admin.findJWT(token=token)
    if admin is None:
        return jsonify({"Error": -1, "msg": "You need to be an admin"})

    if not code:
        return jsonify({"Error": -1, "msg": "Course code is required"})

    course = Course.select_by_code(code=code)
    if course is None:
        return jsonify({"Error": -1, "msg": "Course not found"})

    return jsonify({"Code": 1, "course": course.to_dict()})

def showAll():
    token = request.args.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    admin = Admin.findJWT(token=token)
    if admin is None:
        return jsonify({"Error": -1, "msg": "You need to be an admin"})

    courses = Course.query.all()

    if len(courses) == 0:
        return jsonify({"Error": -1, "msg": "No courses found"})

    courses_list = [course.to_dict() for course in courses]
    return jsonify({"Code": 1, "courses": courses_list})