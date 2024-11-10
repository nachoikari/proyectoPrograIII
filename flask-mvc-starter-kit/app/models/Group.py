from . import db
from app.models.Professor import Professor
from app.models.Course import Course
class Group(db.Model):
    __tablename__ = 'groups'

    nrc = db.Column(db.String(50), primary_key=True)
    group_number = db.Column(db.Integer, nullable=False)
    ced_professor = db.Column(db.String(50), db.ForeignKey('professor.ced'), nullable=False)
    code_course = db.Column(db.String(20), db.ForeignKey('course.code'), nullable=False)
    
    def __repr__(self):
        return f'<Group {self.id}>' 
    
    def to_dict(self):
        professor = Professor.query.get(self.ced_professor)
        course = Course.query.get(self.code_course)
        
        return {
            "NRC": self.nrc,
            "Number group": self.group_number,
            "Course Code": course.code,
            "Course Name": course.name,
            "Career": course.id_career,
            "Professor Ced": professor.ced,
            "Professor Name": professor.name
        }
    
    @classmethod
    def create(cls, nrc, group_number, ced_professor, code_course):
        try:
            new_group = Group(nrc=nrc, 
                        group_number = group_number, 
                        ced_professor = ced_professor, 
                        code_course= code_course)
            db.session.add(new_group)
            db.session.commit()
            return True, new_group
        except Exception as e:
            db.session.rollback()
            print(f"Error creating career: {e}")
            return False, None
    @classmethod
    def update(cls, nrc = None, group_number = None, ced_professor = None,code_course = None, new_nrc=None):
        if nrc is None:
            return False, None
        
        group_update = Group.query.get(nrc)
        
        if group_update is None:
            return False, None
        
        if group_number is not None:
            group_update.group_number = group_number
        if ced_professor is not None:
            group_update.ced_professor = ced_professor
        if code_course is not None:
            group_update.code_course = code_course
        if new_nrc is not None:
            group_update.nrc = new_nrc
        
        try:
            db.session.commit()
            return True, group_update
        except Exception as e:
            db.session.rollback()
            print("Hola")
            print(f"Error updating group: {e}")
            return False, None
    @classmethod
    def delete(cls, nrc = None):
        if nrc is None:
            return False, "NRC is required to delete a group"
        
        delete_group = Group.query.get(nrc)
        
        if delete_group is None:
            return False, "Group not found"
        try:
            db.session.delete(delete_group)
            db.session.commit()
            return True, delete_group
        except Exception as e:
            db.session.rollback()
            print(f"Error removing the group: {e}")
            return False, f"Database error: {e}"
    @classmethod 
    def find_by_NRC(cls, nrc):
        if nrc is None:
            return None, "NRC is required"
        
        group = Group.query.get(nrc)

        if group is None:
            return None, "Group not found"
        
        return True, group
    
       