from . import db

class Career(db.Model):
    __tablename__ = 'careers'

    id = db.Column(db.Integer, primary_key=True)
    

    def __repr__(self):
        return f'<Career {self.id}>'