from . import db

class Faculty(db.Model):
    __tablename__ = 'faculties'

    id = db.Column(db.Integer, primary_key=True)
    

    def __repr__(self):
        return f'<Faculty {self.id}>'