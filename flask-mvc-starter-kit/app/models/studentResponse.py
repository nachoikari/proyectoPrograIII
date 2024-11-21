from . import db
from app.models.Student import Student

class StudentResponse(db.Model):
    __tablename__ = 'student_responses'

    id = db.Column(db.Integer, primary_key=True)
    assignment_id = db.Column(db.Integer, db.ForeignKey('assignments.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False)
    nrc_group = db.Column(db.String(50), nullable=False)
    ced_student = db.Column(db.String(50), db.ForeignKey('students_per_group.ced_student', ondelete='CASCADE', onupdate='CASCADE'), nullable=False)
    url = db.Column(db.String(255), nullable=False)
    submitted_at = db.Column(db.DateTime, nullable=False, default=db.func.current_timestamp())
    grade = db.Column(db.Float, default=None)
    comment = db.Column(db.Text, default=None)

    def __repr__(self):
        return f'<StudentResponse {self.id}>'

    def to_dict(self):
        student = Student.query.get(self.ced_student)
        submitted_at_str = self.submitted_at.strftime('%d/%m/%Y') if self.submitted_at else None
        return{
            "id": self.id,
            "assignment_id": self.assignment_id,
            "nrc_group": self.nrc_group,
            "Student Name":student.name,
            "ced_student": self.ced_student,
            "url": self.url,
            "submitted_at": submitted_at_str,
            "grade": self.grade,
            "comment": self.comment,
        }

    @classmethod
    def create(cls, assignment_id, nrc_group, ced_student, url, grade=None, comment=None):
        try:
            new_response = cls(
                assignment_id=assignment_id,
                nrc_group=nrc_group,
                ced_student=ced_student,
                url=url,
                grade=grade,
                comment=comment
            )
            db.session.add(new_response)
            db.session.commit()
            return True, new_response
        except Exception as e:
            db.session.rollback()
            print(f"Error creating student response: {e}")
            return False, None

    @classmethod
    def update(cls, id, new_grade=None, new_comment=None, new_url=None):
        try:
            response = cls.query.get(id)
            if response is None:
                return False, "Student response not found"

            if new_grade is not None:
                response.grade = new_grade
            if new_comment is not None:
                response.comment = new_comment
            if new_url is not None:
                response.url = new_url

            db.session.commit()
            return True, response
        except Exception as e:
            db.session.rollback()
            print(f"Error updating student response: {e}")
            return False, None

    @classmethod
    def delete(cls, id):
        try:
            response = cls.query.get(id)
            if response is None:
                return False, "Student response not found"

            db.session.delete(response)
            db.session.commit()
            return True
        except Exception as e:
            db.session.rollback()
            print(f"Error deleting student response: {e}")
            return False