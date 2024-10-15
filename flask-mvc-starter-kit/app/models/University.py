from . import db

class University(db.Model):
    __tablename__ = 'universities'

    id = db.Column(db.Integer, primary_key=True)
    

    def __repr__(self):
        return f'<University {self.id}>'