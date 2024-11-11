from app.models.University import University
from flask import request, jsonify

def create():
    name = None
    address = None
    email = None
    token = None
    url = None  # Nueva variable para el URL

    if request.method == "POST":
        name = request.form.get("name")
        address = request.form.get("address")
        email = request.form.get("email")
        url = request.form.get("url")  # Obtener el campo URL
        token = request.form.get("token")

    if not token:
        return '{"Error":-1, "msg":"Token necesario para la petición"}'
    if not name or not address or not email:
        return '{"Error":-1, "msg":"Todos los campos son necesarios"}'

    success, new_university = University.create(email=email, name=name, address=address, url=url)
    
    if success:
        return jsonify({"code": 1, "msg": "Universidad creada exitosamente", "university": new_university.name})
    else:
        return jsonify({"Error": -1, "msg": "Error al crear la universidad"})

def delete():
    id=None
    token=None
    if request.method == "DELETE":
        id = request.form.get("id")
        token = request.form.get("token")
    if id == None or id == "":
        return jsonify({"Error": -1, "msg": "Ingrese el id para eliminar"})
    if token == None or token == "":
         return jsonify({"Error": -1, "msg": "Token necesario"})  
    success, result = University.delete(id=id)
    if success:
        return jsonify({"code": 1, "msg": "La universidad se ha eliminado exitosamente"})
    else:
        return jsonify({"Error": -1, "msg": result})

def update():
    id = None
    name = None
    address = None
    email = None
    token = None
    url = None 

    if request.method == "PUT":
        id = request.form.get("id")
        name = request.form.get("name")
        address = request.form.get("address")
        email = request.form.get("email")
        url = request.form.get("url") 
        token = request.form.get("token")

    if not token:
        return jsonify({"Error": -1, "msg": "Token necesario para la petición"})
    if not id:
        return jsonify({"Error": -1, "msg": "El ID de la universidad es necesario"})

    university_update = University.selectID(id)

    if university_update is None:
        return jsonify({"Error": -1, "msg": "Universidad no encontrada"})

    if name == "":
        name = None
    if address == "":
        address = None
    if email == "":
        email = None
    if url == "": 
        url = None

    success, result = University.update(id=id, new_name=name, new_email=email, new_address=address, new_url=url)

    if success:
        return jsonify({"code": 1, "msg": "Universidad modificada", "university": result.name})
    else:
        return jsonify({"Error": -1, "msg": result})

def showAll():
    # Obtener el token desde los parámetros de la URL
    token = request.args.get("token")

    # Verificar si el token es válido
    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "El token es necesario"})

    # Obtener todas las universidades
    universities = University.query.all()

    if len(universities) == 0:
        return jsonify({"Error": -1, "msg": "No se encontraron universidades"})

    # Convertir universidades a diccionario
    universities_list = [university.to_dict() for university in universities]
    return jsonify({"code": 1, "msg": "Universidades encontradas", "universities": universities_list})

def showID():
    # Obtener los parámetros desde la URL
    id = request.args.get("id")
    token = request.args.get("token")
    
    # Verificar si el token y el id son válidos
    if token is None or token == "":
        return jsonify({"Error": -1, "msg": "El token es necesario"})
    if id is None or id == "":
        return jsonify({"Error": -1, "msg": "El ID es necesario"})

    # Buscar la universidad por ID
    university = University.selectID(id)
    if university is None:
        return jsonify({"Error": -1, "msg": "Universidad no encontrada"})
    else:
        return jsonify({"code": 1, "msg": university.to_dict()})

def showPage():
    token = None
    if request.method == "GET":
        token = request.args.get("token")
    if token is None:
        return jsonify({
            "Error": -1,
            "msg": "Token is required"
        })
    try:
        # Parámetros de la página y límite de elementos
        page = int(request.args.get('page', 1))  # Página actual, por defecto la 1
        per_page = int(request.args.get('per_page', 10))  # Elementos por página, por defecto 10
        universities_paged = University.query.paginate(page=page, per_page=per_page, error_out=False)
        
        # Verificar si hay resultados y definir 'universities'
        if universities_paged.items:
            universities = [university.to_dict() for university in universities_paged.items]
            return jsonify({
                "code": 1,
                "msg": "Universities found",
                "universities": universities,
                "total_pages": universities_paged.pages,
                "current_page": universities_paged.page
            })
        else:
            return jsonify({"Error": -1, "msg": "No universities found"})

    except Exception as e:
        return jsonify({"Error": -1, "msg": f"An error occurred: {e}"})
