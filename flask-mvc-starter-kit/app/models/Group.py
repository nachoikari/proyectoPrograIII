from . import db

class Group(db.Model):
    __tablename__ = 'groups'

    id = db.Column(db.Integer, primary_key=True)
    

    def __repr__(self):
        return f'<Group {self.id}>'