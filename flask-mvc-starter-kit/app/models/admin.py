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
    def to_dict(self):
        return{
            "ced":self.ced,
            "name":self.name,
            "email":self.email,
            "token":self.token
        }
    def selectID(id):
        return Admin.query.get(id)
    
    def findJWT(token):
        return Admin.query.filter_by(token=token).first()
    
    def create_jwt(self):
        try:
            payload = {
                'ced': self.ced,
                'password': self.password
            }           
            token = jwt.encode(payload, "secret", algorithm='HS256')
            return token
        except Exception as e:
            print(f"Error al generar JWT: {e}")
            return None

    @classmethod
    def create(cls, ced, password, name, email):
        try:
            new_admin = cls(ced=ced, password=password, name=name, email=email)
            new_admin.token = new_admin.create_jwt()
            db.session.add(new_admin)
            db.session.commit()
            return True, new_admin
        except Exception as e:
            db.session.rollback()
            print(f"Error al crear admin: {e}")
            return False, None
    @classmethod
    def update(cls, ced, new_name=None, new_email=None):
        try:
            # Obtener el administrador existente
            admin = cls.query.get(ced)  # ced es la clave primaria
            
            if admin is None:
                return False, "Administrador no encontrado."

            # Modificar los atributos según sea necesario
            if new_name is not None:
                admin.name = new_name
            if new_email is not None:
                admin.email = new_email
            if admin.token is None:
                admin.token = admin.create_jwt()

            db.session.commit()
            return True, admin
        except Exception as e:
            db.session.rollback() 
            print(f"Error al actualizar admin: {e}")
            return False, None
    @classmethod
    def delete(cls, ced):
        try:
            admin=cls.query.get(ced)
            db.session.delete(admin)
            db.session.commit()
            return True,admin
        except Exception as e:
            db.session.rollback()
            print(f"Error al elimnar admin: {e}")
            return False, admin