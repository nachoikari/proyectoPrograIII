from . import db
import jwt
class Professor(db.Model):
    __tablename__ = 'professor'

    ced = db.Column(db.String(50), primary_key=True, nullable=False)
    name = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(200), nullable=False)
    password = db.Column(db.String(200), nullable=False)
    token = db.Column(db.String(500), nullable=True)
    id_faculty = db.Column(db.Integer, db.ForeignKey('faculty.id', ondelete="CASCADE", onupdate="CASCADE"), nullable=True)
    phone_number = db.Column(db.String(100), nullable=False)

    def __repr__(self):
        return f'<Professor {self.id}>'
    def to_dict(self):
        return{
            "ced":self.ced,
            "name":self.name,
            "email":self.email,
            "token":self.token,
            "facultad a la que pertenece": self.id_faculty,
            "phone_number": self.phone_number
        }
    
    def selectID(ced):
        return Professor.query.get(ced)
    
    def findJWT(token):
        return Professor.query.filter_by(token=token).first()
    
    def createJWT(self): 
        try:
            payload = {
                'ced':self.ced,
                'password':self.password
            }
            token = jwt.encode(payload,"secret",algorithm='HS256')
            return token
        except Exception as e:
            print (f"Error generando el token: {e}")
            return None
    
    @classmethod
    def create(cls, ced, name, email, password, id_faculty, phone_number):
        try:
            new_professor = cls(
                ced=ced,
                name=name,
                email=email,
                password=password,
                id_faculty=id_faculty,
                phone_number=phone_number
            )
            new_professor.token = new_professor.createJWT()
            db.session.add(new_professor)
            db.session.commit()
            return True, new_professor
        except Exception as e:
            db.session.rollback()
            print(f"Error creando el profesor: {e}")
            return False, None
    
    @classmethod
    def update(cls, ced, new_name=None, new_email=None, new_password=None, new_faculty=None, new_phone_number=None, new_ced=None):
        try:
            professor = cls.query.get(ced)
            if professor is None:
                return False, "Profesor no encontrado."
            if new_name is not None:
                professor.name = new_name
            if new_email is not None:
                professor.email = new_email
            if new_password is not None:
                professor.password = new_password
            if new_faculty is not None:
                professor.id_faculty = new_faculty
            if new_phone_number is not None:
                professor.phone_number = new_phone_number
            if new_ced is not None:
                professor.ced = new_ced
            db.session.commit()
            return True, professor
        except Exception as e:
            db.session.rollback()
            print(f"Error actualizando el profesor: {e}")
            return False, None
    
    @classmethod
    def delete(cls, ced):
        try:
            professor=cls.query.get(ced)
            db.session.delete(professor)
            db.session.commit()
            return True, professor
        except Exception as e:
            db.session.rollback()
            print(f"Error al eliminar el estudiante: {e}")
            return False, None