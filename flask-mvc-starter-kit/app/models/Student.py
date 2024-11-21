from . import db
import jwt
class Student(db.Model):
    __tablename__ = 'student'

    ced = db.Column(db.String(50), primary_key=True, nullable=False)
    name = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(200), nullable=False)
    password = db.Column(db.String(200), nullable=False)
    token = db.Column(db.String(500), nullable=True)
    id_career = db.Column(db.Integer, db.ForeignKey('career.id', ondelete="CASCADE", onupdate="CASCADE"), nullable=False)
    phone_number = db.Column(db.String(100), nullable=False)

    def __repr__(self):
        return f'<Student {self.ced}>'

    def to_dict(self):
        return {
            "ced": self.ced,
            "name": self.name,
            "email": self.email,
            "token": self.token,
            "career": self.id_career,
            "phone_number": self.phone_number
        }
    def to_dict1(self):
        return {
            "ced": self.ced,
            "name": self.name,
            "email": self.email,
            "career": self.id_career,
            "phone_number": self.phone_number,
            "password":self.password
        }    
    @classmethod
    def selectID(cls, ced):
        return cls.query.get(ced)
    
    @classmethod
    def findJWT(cls, token):
        return cls.query.filter_by(token=token).first()
    
    def createJWT(self): 
        try:
            payload = {
                'ced': self.ced,
                'password': self.password
            }
            token = jwt.encode(payload, "secret", algorithm='HS256')
            return token
        except Exception as e:
            print(f"Error generating token: {e}")
            return None

    @classmethod
    def create(cls, ced, name, email, password, id_career, phone_number):
        try:
            new_student = cls(
                ced=ced,
                name=name,
                email=email,
                password=password,
                id_career=id_career,
                phone_number=phone_number
            )
            new_student.token = new_student.createJWT()
            db.session.add(new_student)
            db.session.commit()
            return True, new_student
        except Exception as e:
            db.session.rollback()
            print(f"Error creating student: {e}")
            return False, None

    @classmethod
    def update(cls, ced, new_name=None, new_email=None, new_password=None, new_phone_number=None):
        try:
            student = cls.query.get(ced)
            if student is None:
                return False, "Student not found."

            if new_name is not None:
                student.name = new_name
            if new_email is not None:
                student.email = new_email
            if new_password is not None:
                student.password = new_password
            if new_phone_number is not None:
                student.phone_number = new_phone_number

            db.session.commit()
            return True, student
        except Exception as e:
            db.session.rollback()
            print(f"Error updating student: {e}")
            return False, None

    @classmethod
    def delete(cls, ced):
        try:
            student = cls.query.get(ced)
            db.session.delete(student)
            db.session.commit()
            return True, student
        except Exception as e:
            db.session.rollback()
            print(f"Error deleting student: {e}")
            return False, None
    