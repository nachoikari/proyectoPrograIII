from app.models.Assignments import Assignments
from flask import request, jsonify
from app.models.admin import Admin
from app.models.Professor import Professor
import base64
import os
from datetime import datetime
from app.models.Student import Student
import mimetypes
def create():
    token = None
    url = None
    nrc_group = None
    name = None
    due_date = None
    file_data = None
    file_name = None
    file_type = None
    if request.method == "POST": 
        token = request.form.get("token") 
        nrc_group = request.form.get("nrc_group") 
        name = request.form.get("name") 
        due_date = request.form.get("due_date") 
        file_data = request.form.get("file") 
        file_name = request.form.get("filename") 
        file_type = request.form.get("filetype")
   
    if file_data is None or file_name is None or file_type is None:
        return jsonify({
            "Error": -1,
            "msg": "File data, filename and filetype are required"
        })
    file_bytes =base64.b64decode(file_data)
    extension = mimetypes.guess_extension(file_type)
    if not extension: 
        return jsonify({ 
            "Error": -1, 
            "msg": "Unsupported file type" 
        })
    if not file_name.endswith(extension): 
        file_name += extension
    admin = Admin.findJWT(token=token)
    professor = Professor.findJWT(token=token)

    if admin is None and professor is None:
        return jsonify({
            "Error": -1,
            "msg": "You need to be an admin or professor to create Assignments"
        })

    if nrc_group is None:
        return jsonify({
            "Error": -1,
            "msg": "NRC group is required"
        })
    if name is None:
        return jsonify({
            "Error": -1,
            "msg": "Name assignment is required"
        })
    if due_date is None:
        return jsonify({
            "Error": -1,
            "msg": "Due date is required"
        })

    # Definir el folder de subida
    current_directory = os.path.dirname(os.path.abspath(__file__))
    project_directory = os.path.abspath(os.path.join(current_directory, "..", "..",".."))
    #print(project_directory)
    upload_folder = os.path.join(project_directory, 'Upload')
    if not os.path.exists(upload_folder): 
        os.makedirs(upload_folder)

    # Guardar el archivo
    file_path = os.path.join(upload_folder, file_name)

    with open(file_path, "wb") as file: 
        file.write(file_bytes)
    try:
        due_date = datetime.strptime(due_date, "%d/%m/%Y").strftime("%Y-%m-%d %H:%M:%S")
    except ValueError:
        return jsonify({
            "Error": -1,
            "msg": "Invalid date format. Expected DD/MM/YYYY."
        })
    
    archivodir =None
    for index, char in enumerate(reversed(project_directory)):
        if char == '\\':
            actual_index = len(project_directory)  - index
            substring = project_directory[actual_index:]
            archivodir = substring
            break
    base_dir = os.path.abspath(os.path.dirname(__file__))
    
    url = archivodir + "\\Upload\\"+file_name
    success, new_assignment = Assignments.create(nrc_group=nrc_group, url=url, name=name, due_date=due_date)
    
    if success:
    
        return jsonify({
            "Code": 1,
            "msg": "Assignment created and file uploaded successfully"
        })
    else:
        return jsonify({
            "Error": -1,
            "msg": "Error creating assignment"
        })
    
def delete():
    token = None
    id_file = None
    if request.method == "DELETE":
        token = request.form.get("token")
        id_file = request.form.get("id_file")
    admin = Admin.findJWT(token=token)
    
    prof = Professor.findJWT(token=token)

    if admin is None and prof is None:
        return jsonify({"Error":-1, 
                        "msg":"You need to be a admin or professor to create groups"})
    asssignment = Assignments.query.get(id_file)

    if asssignment is None:
        return jsonify({"Error":-1, 
                        "msg":"Id file is required to delete"})
    current_directory = os.path.dirname(os.path.abspath(__file__))
    project_directory = os.path.abspath(os.path.join(current_directory, "..", "..","..",".."))
    path = project_directory+"\\"+asssignment.url
    normalized_path = os.path.normpath(project_directory)
    #os.remove(path)
    print(normalized_path)
    print(path)
    success,asssignment_delete = Assignments.delete(id=id_file)
    if success:
         os.remove(path)
         return jsonify({"Code": 1, 
                        "msg":"Assignment deleted"})
    else:
        return jsonify({
            "Error": -1,
            "msg": "Error deleting file"
        })
def showPerGroup():
    token = None
    nrc_group = None
    page = None  
    per_page = None

    if request.method == "GET":
        token = request.args.get("token")
        nrc_group = request.args.get("nrc_group")
        page = int(request.args.get('page', 1))
        per_page = int(request.args.get('per_page', 10))
    
    admin = Admin.findJWT(token=token)
    prof = Professor.findJWT(token=token)
    student = Student.findJWT(token=token)
    
    if admin is None and prof is None and student is None:
        return jsonify({"Error": -1, "msg": "You need to be an admin, professor, or student to view assignments"})
    
    assignments_query = Assignments.query.filter_by(nrc_group=nrc_group).paginate(page=page, per_page=per_page, error_out=False)

    assignments = [assignment.to_dict() for assignment in assignments_query.items]

    result = {
        "assignments": assignments,
        "page": assignments_query.page,
        "per_page": assignments_query.per_page,
        "total": assignments_query.total,
        "pages": assignments_query.pages
    }
    
    return jsonify(result) 