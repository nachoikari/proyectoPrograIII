from . import db

class Department(db.Model):
    __tablename__ = 'departments'

    id = db.Column(db.Integer, primary_key=True)
    

    def __repr__(self):
        return f'<Department {self.id}>'