from . import db

class Studentgroup(db.Model):
    __tablename__ = 'students_per_group'

    nrc_group = db.Column(db.String(50), db.ForeignKey('groups.nrc'), primary_key=True)
    ced_student = db.Column(db.String(50), db.ForeignKey('student.ced'), primary_key=True)
    grade = db.Column(db.Float, nullable=True)
    

    def __repr__(self):
        return f'<Studentgroup {self.id}>'
    
    @classmethod
    def registerStudent(cls, ced_student, nrc_group, grade):
        try:
            new_studentGroup = cls(ced_student = ced_student,
                                   nrc_group = nrc_group,
                                   grade = grade)
            
            db.session.add(new_studentGroup)
            db.session.commit()
            return True, new_studentGroup
        except Exception as e:
            db.session.rollback()
            print(f"Error adding student {ced_student}  in the group {nrc_group}: {e}")
            return False, str(e)
    @classmethod
    def delete(cls,ced_student, nrc_group ):
        try:
            student_group = cls.query.filter_by(ced_student=ced_student, nrc_group=nrc_group).first()
            
            if student_group is None:
                return False, "Record not found."
            
            db.session.delete(student_group)
            db.session.commit()
            return True, "Student unenrolled successfully."
        except Exception as e:
            db.session.rollback()
            print(f"Error unenroll student {ced_student}  in the group {nrc_group}: {e}")
            return False, str(e)
    @classmethod 
    def update(cls, ced_student, nrc_group, grade):
        print()