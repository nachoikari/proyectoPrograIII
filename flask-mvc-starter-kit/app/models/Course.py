from . import db

class Course(db.Model):
    __tablename__ = 'courses'

    id = db.Column(db.Integer, primary_key=True)
    

    def __repr__(self):
        return f'<Course {self.id}>'