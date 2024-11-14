from . import db
from datetime import datetime
class Assignments(db.Model):
    __tablename__ = 'assignments'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    nrc_group = db.Column(db.String(50), db.ForeignKey('groups.nrc', ondelete="CASCADE", onupdate="CASCADE"), nullable=False)
    url = db.Column(db.String(100), nullable=False)
    name = db.Column(db.String(100), nullable=False)
    due_date = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    
    def __repr__(self):
        return f"<Assignment(id={self.id}, nrc_group={self.nrc_group}, name='{self.name}', due_date={self.due_date})>"
    def to_dict(self):
        return ({
            "id": self.id,
            "nrc_group": self.nrc_group,
            "url": self.url,
            "name": self.name,
            "due_date": self.due_date.strftime("%d/%m/%Y")
        })
    @classmethod
    def create(cls, nrc_group, url, name, due_date):
        try:
            new_assignment = cls(nrc_group=nrc_group, url=url,
                                  name=name,due_date=due_date)
            db.session.add(new_assignment)
            db.session.commit()
            return True, new_assignment
        except Exception as e:
            db.session.rollback()
            print(f"Error creating asssignment: {e}")
            return False, None
    @classmethod
    def delete(cls,id):
        try:
            assignment = Assignments.query.get(id)
            if assignment is None:
                    return False, None
            db.session.delete(assignment)
            db.session.commit()
            return True, "Assignment deleted "
        except Exception as e:
            db.session.rollback()
            print(f"Error deleting assignment: {e}")
            return False, None

    
