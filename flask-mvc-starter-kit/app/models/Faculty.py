from . import db
from app.models.University import University

class Faculty(db.Model):
    __tablename__ = 'faculty'
    id = db.Column(db.Integer, primary_key=True,autoincrement=True, nullable=False)
    name = db.Column(db.String(200), nullable=False)
    id_university = db.Column(db.Integer, db.ForeignKey('university.id', ondelete="CASCADE", onupdate="CASCADE"), nullable=False)

    def __repr__(self):
        return f'<Faculty {self.id}>'
    def to_dict(self):
        # Obtener la universidad relacionada
        university = University.query.get(self.id_university)
        
        return {
            "id": self.id,
            "name": self.name,
            "University belongs": university.name if university else -1  # Verifica si existe la universidad
        }
    def selectID(id):
        return Faculty.query.get(id)
    @classmethod
    def create(cls, name,id_universidad):
        try:
            new_faculty = cls(id_university=id_universidad, name=name)
            db.session.add(new_faculty)
            db.session.commit()
            return True, new_faculty
        except Exception as e:
            db.session.rollback()
            print(f"Error al crear la facultad:{e}")
            return False, None
    
    @classmethod
    def update(cls, id_faculty, new_name=None, id_university=None):
        try:
            faculty_update = cls.query.get(id_faculty)
            if faculty_update is None:
                return False, "Facultad no existe"

            # Solo actualizar los campos que se proporcionen
            if new_name is not None:
                faculty_update.name = new_name
            if id_university is not None:  # Si no se proporciona, no se actualiza
                faculty_update.id_university = id_university
            
            db.session.commit()
            return True, faculty_update

        except Exception as e:
            db.session.rollback()
            return False, f"Error al actualizar la facultad: {e}"
    
    @classmethod
    def delete(cls, id):
        faculty = None
        faculty = cls.query.get(id)
        if faculty == None:
            print(f"Error la facultad no existe")
            return False, None
        db.session.delete(faculty)
        db.session.commit()
        return True, faculty    
