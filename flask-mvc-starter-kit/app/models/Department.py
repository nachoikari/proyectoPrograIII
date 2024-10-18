from . import db
from app.models.Faculty import Faculty
class Department(db.Model):
    __tablename__ = 'department'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True, nullable=False)
    name = db.Column(db.String(100), nullable=False)
    id_Faculty = db.Column(db.Integer, db.ForeignKey('faculty.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False)

    def __repr__(self):
        return f'<Department {self.id}>'
    def to_dict(self):
        fac= Faculty.query.get(self.id_Faculty)
        return{
            "name":self.name,
            "id":self.id,
            "Faculty to which the belongs" :fac.name
        }



    @classmethod
    def create(cls, name, id_faculty):
        try:
            dept = cls(name=name,id_Faculty=id_faculty)
            db.session.add(dept)
            db.session.commit()
            return True, dept
        except Exception as e:
            db.session.rollback()
            print (f"Error creating department:{e}")
            return False,None
    @classmethod
    def update(cls,id,new_name=None,new_idFac=None):
        try:
            dept = Department.query.get(id)
            if dept is None:
                return False, "Department doesnt exist"
            if new_name is not None:
                dept.name = new_name
            if new_idFac is not None:
                dept.id_Faculty = new_idFac
            db.session.commit()
            return True, dept
        except Exception as e:
            db.session.rollback()
            print(f"Error updating department:{e}")
            return False,None
    
    @classmethod
    def delete(cls,id):
        try:
            dept = Department.query.get(id)
            db.session.delete(dept)
            db.session.commit()
            return True,dept
        except Exception as e:
            db.session.rollback()
            print(f"Error deleting department:{e}")
            return False,None