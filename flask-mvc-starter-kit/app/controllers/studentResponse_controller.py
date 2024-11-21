from app.models.studentResponse import StudentResponse
from app.models.admin import Admin
from app.models.Student import Student
from app.models.Assignments import Assignments
from app.models.Professor import Professor
from flask import request, jsonify
from sqlalchemy.sql import func
import random
from app.models.ia_response import IaResponse
import base64
import mimetypes
import os
import binascii
from datetime import datetime

def create():
    token = None
    student_id = None
    assignment_id = None
    grade = None
    comment = None
    file_data = None
    file_name = None
    file_type = None
    
    if request.method == "POST":
        token = request.form.get("token")
        student_id = request.form.get("student_id")
        assignment_id = request.form.get("assignment_id")
        grade = request.form.get("grade")
        comment = request.form.get("comment")
        file_data = request.form.get("file")
        file_name = request.form.get("filename")
        file_type = request.form.get("filetype")
    
    if not all([student_id, assignment_id]):
        return jsonify({
            "Error": -1,
            "msg": "Student ID and Assignment ID are required"
        })
    
    if file_data is None or file_name is None or file_type is None:
        return jsonify({
            "Error": -1,
            "msg": "File data, filename, and filetype are required"
        })

    if file_data:

        padding_needed = len(file_data) % 4
        if padding_needed:
            file_data += '=' * (4 - padding_needed)

        try:
            file_bytes = base64.b64decode(file_data)
        except binascii.Error as e:
            return jsonify({
                "Error": -1,
                "msg": "Error decoding base64: " + str(e)
            })
    
    extension = mimetypes.guess_extension(file_type)
    if not extension:
        return jsonify({
            "Error": -1,
            "msg": "Unsupported file type"
        })
    
    if not file_name.endswith(extension):
        file_name += extension


    student = Student.query.get(student_id)
    if student is None:
        return jsonify({
            "Error": -1,
            "msg": "Student not found"
        })


    assignment = Assignments.query.get(assignment_id)
    if assignment is None:
        return jsonify({
            "Error": -1,
            "msg": "Assignment not found"
        })

    current_directory = os.path.dirname(os.path.abspath(__file__))
    project_directory = os.path.abspath(os.path.join(current_directory, "..", "..", ".."))
    upload_folder = os.path.join(project_directory, 'Upload')
    if not os.path.exists(upload_folder):
        os.makedirs(upload_folder)

    file_path = os.path.join(upload_folder, file_name)

    with open(file_path, "wb") as file:
        file.write(file_bytes)
    
    nrc_group = assignment.nrc_group
    success, new_response = StudentResponse.create(
        assignment_id=assignment_id,
        nrc_group=nrc_group,  # Se agrega el nrc_group
        ced_student=student_id,  # Cambiado de student_id para coincidir con el modelo
        url=file_path,  # Cambiado de file_url para coincidir con el modelo
        grade=grade,
        comment=comment
    )
    
    if success:
        return jsonify({
            "code": 1,
            "msg": "Student response created and file uploaded successfully"
        })
    else:
        return jsonify({
            "Error": -1,
            "msg": "Error creating student response"
        })

def delete():

    token = None
    id_response = None
    
    if request.method == "DELETE":
        token = request.form.get("token")
        id_response = request.form.get("id_response")
    

    student = Student.findJWT(token=token)
    
    if student is None:
        return jsonify({
            "Error": -1,
            "msg": "You need to be an admin or professor to delete student responses"
        })
    
    # Obtener la respuesta del estudiante
    response = StudentResponse.query.get(id_response)
    
    if response is None:
        return jsonify({
            "Error": -1,
            "msg": "Response not found"
        })
    
    # Ruta del archivo asociado
    current_directory = os.path.dirname(os.path.abspath(__file__))
    project_directory = os.path.abspath(os.path.join(current_directory, "..", "..", ".."))
    file_path = os.path.join(project_directory, response.url)
    normalized_path = os.path.normpath(file_path)
    
    try:
        # Eliminar el archivo del sistema si existe
        if os.path.exists(normalized_path):
            os.remove(normalized_path)
        
        # Eliminar la respuesta del estudiante de la base de datos
        success = StudentResponse.delete(id=id_response)
        
        if success:
            return jsonify({
                "code": 1,
                "msg": "Student response and file deleted successfully"
            })
        else:
            return jsonify({
                "Error": -1,
                "msg": "Error deleting student response"
            })
    except Exception as e:
        return jsonify({
            "Error": -1,
            "msg": f"An error occurred: {str(e)}"
        })

def showPerGroup():

    token = None
    nrc_group = None
    page = 1
    per_page = 10

    if request.method == "GET":
        token = request.args.get("token")
        nrc_group = request.args.get("nrc_group")
        page = int(request.args.get("page", 1))
        per_page = int(request.args.get("per_page", 10))

    if token is None :
        return jsonify({
            "Error": -1,
            "msg": "Token is required"
        })

    pagination = StudentResponse.query.filter_by(nrc_group=nrc_group).paginate(page=page, per_page=per_page, error_out=False)

    if pagination.total == 0:
        return jsonify({
            "Error": -1,
            "msg": f"No responses found for group {nrc_group}"
        })

    response_list = []
    for response in pagination.items:
        assignment = Assignments.query.get(response.assignment_id)
        student = Student.query.get(response.ced_student)
        response_list.append({
            "id": response.id,
            "assignment_id": response.assignment_id,
            "assignment_name": assignment.name if assignment else "Unknown",
            "student_id": response.ced_student,
            "student_name": student.name if student else "Unknown",
            "url": response.url,
            "submitted_at": response.submitted_at.strftime("%Y-%m-%d %H:%M:%S"),
            "grade": response.grade,
            "comment": response.comment
        })

    return jsonify({
        "code": 1,
        "msg": "Responses retrieved successfully",
        "responses": response_list,
        "pagination": {
            "current_page": pagination.page,
            "per_page": pagination.per_page,
            "total_pages": pagination.pages,
            "total_responses": pagination.total
        }
    })

def showPerAssignment():
    token = None
    assignment_id = None

    if request.method == "GET":
        token = request.args.get("token")
        assignment_id = request.args.get("id_assignment")

    # Verificar que el token exista
    if token is None or assignment_id is None:
        return jsonify({
            "Error": -1,
            "msg": "Token and assignment ID are required"
        })

    # Validar token del usuario
    admin = Admin.findJWT(token=token)
    prof = Professor.findJWT(token=token)

    if admin is None and prof is None:
        return jsonify({
            "Error": -1,
            "msg": "Only professor and admin can view responses"
        })

    try:
        id_assignment = int(assignment_id)
    except ValueError:
        return jsonify({
            "Error": -1,
            "msg": "Assignment ID must be a valid integer"
        })

    responses = StudentResponse.query.filter_by(assignment_id=assignment_id).all()

    if not responses:
        return jsonify({
            "Error": -1,
            "msg": f"No responses found for assignment ID {id_assignment}"
        })

    response_list = [response.to_dict() for response in responses]

    return jsonify({
        "code": 1,
        "msg": "Responses retrieved successfully",
        "responses": response_list
    })

def manualEvaluate():
    if request.method == "PUT":
        # Obtener parámetros de la solicitud
        token = request.form.get("token")
        comment = request.form.get("comment")
        grade = request.form.get("grade")
        id_response = request.form.get("id_response")

        admin = Admin.findJWT(token=token)
        prof = Professor.findJWT(token=token)

        if admin is None and prof is None:
            return jsonify({
                "Error": -1,
                "msg": "Only professor and admin can evaluate task"
            })

        if not id_response or grade is None:
            return jsonify({
                "Error": -1,
                "msg": "ID of the response and grade are required"
            })

        try:
            grade = float(grade) 
            if grade < 0 or grade > 100:
                return jsonify({
                    "Error": -1,
                    "msg": "Grade must be between 0 and 100"
                })
        except ValueError:
            return jsonify({
                "Error": -1,
                "msg": "Grade must be a valid number"
            })

        success, response = StudentResponse.update(
            id=id_response,
            new_grade=grade,
            new_comment=comment
        )

        if not success:
            return jsonify({
                "Error": -1,
                "msg": "Failed to evaluate the task"
            })

        return jsonify({
            "code": 1,
            "msg": "Task evaluated successfully",
            "response": response.to_dict()
        })

    return jsonify({
        "Error": -1,
        "msg": "Invalid HTTP method"
    })
    
def iaEvaluate():
    if request.method == "PUT":
        # Obtener parámetros de la solicitud
        token = request.form.get("token")
        comment = request.form.get("comment")
        id_response = request.form.get("id_response")
        
        if not id_response:
            return jsonify({"code": 0, "msg": "ID de respuesta no proporcionado"}), 400

        # Simular una "IA" generando una calificación aleatoria (0 a 100)
        grade = random.randint(0, 100)

        # Determinar la calidad basada en la calificación
        if grade <= 20:
            quality = 1
        elif grade <= 40:
            quality = 2
        elif grade <= 60:
            quality = 3
        elif grade <= 80:
            quality = 4
        else:
            quality = 5

        # Consultar una respuesta aleatoria de la tabla ia_responses según la calidad
        ia_response = IaResponse.query.filter_by(quality=quality).order_by(func.random()).first()
        ia_comment = ia_response.text if ia_response else "Sin respuesta disponible"

        # Intentar actualizar la respuesta del estudiante usando el método de modelo
        success, updated_response = StudentResponse.update(
            id=id_response,
            new_grade=grade,
            new_comment=comment or ia_comment
        )

        if not success:
            return jsonify({"code": 0, "msg": "Error al actualizar la respuesta del estudiante"}), 404

        return jsonify({
            "code": 1,
            "msg": "Calificación asignada con éxito",
            "grade": grade,
            "comment": updated_response.comment,
            "ai_comment": ia_comment
        })

