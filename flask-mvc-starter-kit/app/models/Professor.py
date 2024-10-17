from . import db

class Professor(db.Model):
    __tablename__ = 'professors'

    id = db.Column(db.Integer, primary_key=True)
    

    def __repr__(self):
        return f'<Professor {self.id}>'