from . import db

class Student(db.Model):
    __tablename__ = 'students'

    id = db.Column(db.Integer, primary_key=True)
    

    def __repr__(self):
        return f'<Student {self.id}>'