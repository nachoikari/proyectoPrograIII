from . import db
from app.models.Career import Career

class Course(db.Model):
    __tablename__ = 'course'

    code = db.Column(db.String(20), primary_key=True, nullable=False)
    name = db.Column(db.String(50), nullable=False)
    id_career = db.Column(db.Integer, db.ForeignKey('career.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False)

    def __repr__(self):
        return f'<Course {self.code}>'

    def to_dict(self):
        career = Career.query.get(self.id_career)
        return {
            "Course Code": self.code,
            "Course Name": self.name,
            "Career": career.name if career else "Unknown Career"
        }

    @classmethod
    def select_by_code(cls, code):
        return cls.query.get(code)

    @classmethod
    def create(cls, code, name, id_career):
        try:
            new_course = cls(code=code, name=name, id_career=id_career)
            db.session.add(new_course)
            db.session.commit()
            return True, new_course
        except Exception as e:
            db.session.rollback()
            print(f"Error creating course: {e}")
            return False, None

    @classmethod
    def update(cls, code, new_name=None, new_id_career=None):
        try:
            course = cls.query.get(code)
            if course is None:
                return False, "Course not found"
            
            if new_name is not None:
                course.name = new_name
            if new_id_career is not None:
                course.id_career = new_id_career

            db.session.commit()
            return True, course
        except Exception as e:
            db.session.rollback()
            print(f"Error updating course: {e}")
            return False, None

    @classmethod
    def delete(cls, code):
        try:
            course = cls.query.get(code)
            if course is None:
                return False, "Course not found"
            
            db.session.delete(course)
            db.session.commit()
            return True
        except Exception as e:
            db.session.rollback()
            print(f"Error deleting course: {e}")
            return False
    
