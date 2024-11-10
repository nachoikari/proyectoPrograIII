from . import db
from app.models.Department import Department

class Career(db.Model):
    __tablename__ = 'career'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True, nullable=False)
    name = db.Column(db.String(100), nullable=True)
    id_department = db.Column(db.Integer, db.ForeignKey('department.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False)

    def __repr__(self):
        return f'<Career {self.id}>'

    def to_dict(self):
        department = Department.query.get(self.id_department)
        return {
            "Career": self.name,
            "Department": department.name if department else "Unknown Department",
            "Id": self.id
        }

    @classmethod
    def selectID(cls, id):
        return cls.query.get(id)

    @classmethod
    def create(cls, name, id_department):
        try:
            new_career = cls(name=name, id_department=id_department)
            db.session.add(new_career)
            db.session.commit()
            return True, new_career
        except Exception as e:
            db.session.rollback()
            print(f"Error creating career: {e}")
            return False, None

    @classmethod
    def update(cls, id, new_name=None):
        try:
            career = cls.query.get(id)
            if career is None:
                return False, "Career not found"

            if new_name is not None:
                career.name = new_name

            db.session.commit()
            return True, career
        except Exception as e:
            db.session.rollback()
            print(f"Error updating career: {e}")
            return False, None

    @classmethod
    def delete(cls, id):
        try:
            career = cls.query.get(id)
            if career is None:
                return False, "Career not found"
            
            db.session.delete(career)
            db.session.commit()
            return True, career
        except Exception as e:
            db.session.rollback()
            print(f"Error deleting career: {e}")
            return False, None
