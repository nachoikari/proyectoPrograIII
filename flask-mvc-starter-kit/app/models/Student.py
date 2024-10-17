from . import db
import jwt
class Student(db.Model):
    __tablename__ = 'student'

    ced = db.Column(db.String(50), primary_key=True, nullable=False)
    name = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(200), nullable=False)
    password = db.Column(db.String(200), nullable=False)
    token = db.Column(db.String(500), nullable=True)
    id_faculty = db.Column(db.Integer, db.ForeignKey('faculty.id', ondelete="CASCADE", onupdate="CASCADE"), nullable=True)
    
    def __repr__(self):
        return f'<Student {self.id}>'
    
    def to_dict(self):
        return{
            "ced":self.ced,
            "name":self.name,
            "email":self.email,
            "token":self.token,
            "facultad a la que pertenece": self.id_faculty
        }
    def selectID(ced):
        return Student.query.get(ced)
    def findJWT(token):
        return Student.query.filter_by(token=token).first()
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
    def create(cls, ced,name,email,password,id_faculty):
        try:
            new_student = cls(ced=ced, name=name,email=email,password=password,id_faculty=id_faculty)
            #new_student.token=new_student.createJWT()
            db.session.add(new_student)
            db.session.commit()
            return True, new_student
        except Exception as e:
            db.session.rollback()
            print (f"Error generando el token: {e}")
            return False,None
    
    @classmethod
    def update(cls,ced,new_name=None,new_email=None,new_password=None,new_faculty=None,new_ced=None):
        try:
            student = Student.query.get(ced)
            if student is None:
                return False, "Estudiante no encontrado."
            if new_name is not None:
                student.name = new_name
            if new_email is not None:
                student.email = new_email
            if new_password is not None:
                student.password = new_password
            if new_faculty is not None:
                student.id_faculty = new_faculty
            if new_ced is not None:
                student.ced = new_ced
            db.session.commit()
            return True,student
        except Exception as e:
            db.session.rollback()
            print(f"Error al actualizar el estudiante: {e}")
            return False, None
    
    @classmethod
    def delete(cls, ced):
        try:
            student=cls.query.get(ced)
            db.session.delete(student)
            db.session.commit()
            return True, student
        except Exception as e:
            db.session.rollback()
            print(f"Error al eliminar el estudiante: {e}")
            return False, None
    