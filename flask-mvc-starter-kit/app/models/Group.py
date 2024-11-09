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
        return{
            "Course": Course.to_dict(course),
            "Number group":self.group_number,
            "Proffesor":Professor.to_dict(professor),
            "NRC": self.nrc
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
    
    
       