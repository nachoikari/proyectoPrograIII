from . import db

class Assignments(db.Model):
    __tablename__ = 'assignment'

    id = db.Column(db.Integer, primary_key=True)
    

    def __repr__(self):
        return f'<Assignments {self.id}>'