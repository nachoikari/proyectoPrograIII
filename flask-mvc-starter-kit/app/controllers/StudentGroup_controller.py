from app.models.StudentGroup import Studentgroup
from app.models.Group import Group
from app.models.Student import Student
from app.models.admin import Admin
from app.models.Professor import Professor
from app.models.Course import Course
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
    #puede que un estudiante intente eliminar otro, entonces con el token obtenemos el estudiante
    #que desea hacer la peticion y compara las cedulas, y si la cedula del que quiere eliminar
    #es diferente a la cedula del que realiza la peticion(quiere eliminar un estudiante distinto a el)
    #no lo deja hacer la peticion
    if student is not None:
        stu_ced = student.ced
        if student is not None and ced_student != stu_ced:
            return jsonify({"Error": -1, "msg": "Student can not delete a other student"})
        
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

    if request.method == "POST":
        token = request.form.get("token")
        ced_student = request.form.get("ced_student")
        nrc_group = request.form.get("nrc_group")
        grade = request.form.get("grade")
    
    # Verificar autenticación
    admin = Admin.findJWT(token=token)
    prof = Professor.findJWT(token=token)
    student = Student.findJWT(token=token)

    if admin is None and prof is None and student is None:
        return jsonify({"Error": -1, "msg": "You need to be an admin, professor, or student to update a grade"})

    if student is not None:
        return jsonify({"Error": -1, "msg": "Students can not update their grades"})

    success, result = Studentgroup.update(ced_student=ced_student, nrc_group=nrc_group, grade=grade)
    
    if success:
        return jsonify({"code": 1, "msg": "Grade updated successfully", "student_group": result.to_dict()})
    else:
        return jsonify({"Error": -1, "msg": f"An error occurred: {result}"})
def showPerGroup():
    token = None
    nrc_group = None
    
    # Obtener el token y el nrc_group de los parámetros GET
    if request.method == "GET":
        token = request.args.get("token")
        nrc_group = request.args.get("nrc")
    
    # Validar que el token sea válido
    admin = Admin.findJWT(token=token)
    prof = Professor.findJWT(token=token)
    student = Student.findJWT(token=token)
    
    if admin is None and prof is None and student is None:
        return jsonify({"Error": -1, "msg": "You need to be an admin, professor, or student to get the students list"})
    
    # Verificar si el token y el nrc_group están presentes
    if token is None:
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    if nrc_group is None:
        return jsonify({"Error": -1, "msg": "NRC group is required"})
    
    try:
        page = int(request.args.get('page', 1))
        per_page = int(request.args.get('per_page', 10))
        
        students_list = Studentgroup.query.filter_by(nrc_group=nrc_group).paginate(page=page, per_page=per_page, error_out=False)
        
        # Si la lista de estudiantes no está vacía
        if students_list.items:
            students_data = []
            for student_group in students_list.items:
                # Obtener el estudiante relacionado a partir del ced_student (clave foránea)
                student_info = Student.query.filter_by(ced=student_group.ced_student).first()

                if student_info:

                    students_data.append({
                        "ced": student_info.ced,
                        "name": student_info.name,
                        "email": student_info.email,
                        "grade": student_group.grade,
                        "career": student_info.id_career,
                        "phone_number":student_info.phone_number
                    })
            
            return jsonify({
                "Code": 1,
                "msg": "Success",
                "data": students_data,
                "total": students_list.total,
                "page": page,
                "per_page": per_page
            })
        else:
            return jsonify({
                "Error": -1,
                "msg": "No students found for the given NRC group"
            })
    
    except Exception as e:
        return jsonify({
            "Error": -1,
            "msg": f"An error occurred: {str(e)}"
        })

def showStudentGroups():
    token = None
    ced_student  = None
    
    # Obtener el token y el ced_student de los parámetros GET
    if request.method == "GET":
        token = request.args.get("token")
        ced_student = request.args.get("ced")
    
    # Validar que el token sea válido
    admin = Admin.findJWT(token=token)
    prof = Professor.findJWT(token=token)
    student = Student.findJWT(token=token)
    
    if admin is None and prof is None and student is None:
        return jsonify({"Error": -1, "msg": "You need to be an admin, professor, or student to get the students list"})
    
    if token is None:
        return jsonify({"Error": -1, "msg": "Token is required"})
    
    if ced_student is None:
        return jsonify({"Error": -1, "msg": "Student ced is required"})
    
    try:
        page = int(request.args.get('page', 1))
        per_page = int(request.args.get('per_page', 10)) 
        groups_enrolled = Studentgroup.query.filter_by(ced_student=ced_student).paginate(page=page, per_page=per_page, error_out=False)

        if groups_enrolled.items:
            groups_data = []
            for group in groups_enrolled.items:
                # Usar `nrc` en lugar de `nrc_group`
                group_info = Group.query.filter_by(nrc=group.nrc_group).first()  # Corregido aquí
                if group_info:
                    profe = Professor.query.get(group_info.ced_professor)
                    course = Course.query.get(group_info.code_course)  # Cambié nrc_group por code_course
                    groups_data.append({
                        "nrc": group_info.nrc,
                        "name": course.name if course else "N/A",  # Protege si no se encuentra el curso
                        "Professor": profe.name if profe else "N/A",  # Protege si no se encuentra el profesor
                        "Code course": group_info.code_course
                    })
            
            return jsonify({
                "Error": 0,
                "msg": "Success",
                "data": groups_data,
                "total": groups_enrolled.total,
                "page": page,
                "per_page": per_page
            })
        else:
            return jsonify({
                "Error": -1,
                "msg": "No groups found for the given student"
            })
    
    except Exception as e:
        return jsonify({
            "Error": -1,
            "msg": f"An error occurred: {str(e)}"
        })
