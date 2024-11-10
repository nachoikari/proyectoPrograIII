from app.models.Group import Group
from app.models.admin import Admin
from app.models.Professor import Professor
from app.models.Course import Course
from app.models.Student import Student
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
    
    prof = Professor.findJWT(token=token)
    
    if admin is None and prof is None:
        return jsonify({"Error":-1, "msg":"You need to be a admin or professor to create groups"})
    
    professor = Professor.query.get(ced_professor)
    
    if professor is None:
        return jsonify({"Error":-1, "msg":"Proffesor does not exist"})

    course = Course.query.get(code_course)
    
    if course is None:
        return jsonify({"Error":-1, "msg":"Course does not exist"})
    
    success, new_group = Group.create(nrc=nrc,group_number=group_number,
                                    ced_professor=ced_professor,
                                    code_course=code_course)
    if success:
        return jsonify({"Code": 1, "msg":"Group created"})
    else:
        return jsonify({"Error":-1, "msg":"Error creating the new group"})

def update():
    nrc = None
    group_number = None
    ced_professor = None
    code_course = None
    token = None
    new_nrc = None

    if request.method == "PUT":
        nrc = request.form.get("nrc")
        group_number = request.form.get("group_number")
        ced_professor = request.form.get("ced_professor")
        code_course = request.form.get("code_course")
        token = request.form.get("token")
        new_nrc = request.form.get("new_nrc")
    
    # Verificar si el token del administrador es válido
    admin = Admin.findJWT(token=token)
    
    prof = Professor.findJWT(token=token)
    
    if admin is None and prof is None:
        return jsonify({"Error":-1, "msg":"You need to be a admin or professor to update groups"}), 403

    # Verificar si el grupo existe
    group = Group.query.get(nrc)
    if group is None:
        return jsonify({"Error": -1, "msg": "Group does not exist"}), 404

    # Verificar si el curso existe
    course = Course.query.get(code_course)
    if course is None and code_course is not None:
        return jsonify({"Error": -1, "msg": "Course does not exist"}), 404
    
    # Llamar al método `update` de `Group` para actualizar el grupo
    success, updated_group = Group.update(
        nrc=nrc,
        group_number=group_number,
        ced_professor=ced_professor,
        code_course=code_course,
        new_nrc=new_nrc
    )
    if success:
        return jsonify({
            "Success": 1,
            "msg": "Group updated successfully",
            "group": updated_group.to_dict()
        }), 200
    else:
        return jsonify({"Error": -1, "msg": "Failed to update group"}), 500

def delete():
    token = None
    nrc = None
    if request.method == "DELETE":
        nrc = request.form.get("nrc")
        token = request.form.get("token")
    
    admin = Admin.findJWT(token=token)
    
    prof = Professor.findJWT(token=token)
    
    if admin is None and prof is None:
        return jsonify({"Error":-1, "msg":"You need to be a admin or professor to delete groups"})
    group_to_delete = Group.query.get(nrc)

    if group_to_delete is None:
        return jsonify({"Error":-1, "msg":"Group does not exist"})

    success, group = Group.delete(nrc=nrc)

    if success:
        return jsonify({"Code": 1, "msg": "Group successfully eliminated"}),200
    else:
        return jsonify({"Error": -1, "msg": "Error removing Group"}),500

def showNRC():
    token = None
    nrc = None
    if request.method == "GET":
        token = request.args.get("token")
        nrc = request.args.get("nrc")
    if not token:
        return jsonify({"Error": -1, "msg": "Token is required"}), 400
    if not nrc:
        return jsonify({"Error": -1, "msg": "NRC is required"}), 400
    admin = Admin.findJWT(token=token)
    
    student = Student.findJWT(token=token)

    professor = Professor.findJWT(token=token)

    if admin is None and professor is None and student is None:
        return jsonify({"Error":-1, "msg":"You need to be a admin, professor or student to watch the groups"})
    
    success, group = Group.find_by_NRC(nrc=nrc)

    if success:
        return jsonify({"Code": 1, "group": group.to_dict()})
    else:
        return jsonify({"Error": -1, "msg": "Error searching group"})

def show_groups():
    try:
        # Parámetros de la página y límite de elementos
        page = int(request.args.get('page', 1))  # Página actual, por defecto la 1
        per_page = 10  # Número de elementos por página

        # Obtener los grupos con paginación
        groups_paginated = Group.query.paginate(page=page, per_page=per_page, error_out=False)

        # Verificar si hay resultados
        if groups_paginated.items:
            groups = [group.to_dict() for group in groups_paginated.items]
            return jsonify({
                "code": 1,
                "msg": "Groups found",
                "groups": groups,
                "total_pages": groups_paginated.pages,
                "current_page": groups_paginated.page
            })
        else:
            return jsonify({"Error": -1, "msg": "No groups found"})
    except Exception as e:
        return jsonify({"Error": -1, "msg": f"An error occurred: {e}"})
