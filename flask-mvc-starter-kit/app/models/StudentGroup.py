from . import db

class Studentgroup(db.Model):
    __tablename__ = 'studentgroups'

    id = db.Column(db.Integer, primary_key=True)
    

    def __repr__(self):
        return f'<Studentgroup {self.id}>'