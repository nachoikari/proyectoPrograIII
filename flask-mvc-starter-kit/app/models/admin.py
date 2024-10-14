from . import db
import jwt
import datetime

class Admin(db.Model):
    __tablename__ = 'admin'

    ced = db.Column(db.String(200), primary_key=True, nullable=False, unique=True)
    password = db.Column(db.String(200), nullable=False, unique=True)
    name = db.Column(db.String(200), nullable=False)
    email = db.Column(db.String(200), nullable=False, unique=True)
    token = db.Column(db.String(500), nullable=True)  # Cambié el nombre de la columna para evitar confusión con la librería `jwt`
    
    def __repr__(self):
        return f'<Admin {self.ced}>'
    def selectID(id):
        return Admin.query.get(id)
    def create_jwt(self):
        try:
            # Generar el payload del JWT
            payload = {
                'ced': self.ced,
                'iat': datetime.datetime.utcnow()  # Fecha de creación
            }
            # Crear el token JWT
            token = jwt.encode(payload, "claveSecretajeje", algorithm='HS256')
            return token
        except Exception as e:
            print(f"Error al generar JWT: {e}")
            return None

    def create(cls, ced, password, name, email):
        try:
            # Crear el nuevo administrador
            new_admin = cls(ced=ced, password=password, name=name, email=email)
            db.session.add(new_admin)
            db.session.commit()

            # Generar el JWT y guardarlo
            new_admin.jwt_token = new_admin.create_jwt()
            db.session.commit()  # Guardar el token en la base de datos

            return True, new_admin
        except Exception as e:
            db.session.rollback()  # Hacer rollback en caso de error
            print(f"Error al crear admin: {e}")
            return False, None
