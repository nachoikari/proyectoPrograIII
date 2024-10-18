from . import db

class University(db.Model):
    __tablename__ = 'university'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True, nullable=False)
    name = db.Column(db.String(200), nullable=False)
    email = db.Column(db.String(200), nullable=False)
    address = db.Column(db.String(500), nullable=False)
    url = db.Column(db.String(500), nullable=True)  # Nueva columna

    def __repr__(self):
        return f'<University {self.id}>'

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "email": self.email,
            "address": self.address,
            "url": self.url 
        }

    @classmethod
    def selectID(cls, id):
        return University.query.get(id)

    @classmethod
    def create(cls, name, email, address, url=None):
        try:
            new_university = cls(name=name, email=email, address=address, url=url)
            db.session.add(new_university)
            db.session.commit()
            return True, new_university
        except Exception as e:
            db.session.rollback()
            print(f"Error al crear la universidad: {e}")
            return False, None

    @classmethod
    def delete(cls, id):
        university = cls.query.get(id)
        if university is None:
            return False, "La universidad no existe"
        db.session.delete(university)
        db.session.commit()
        return True, university

    @classmethod
    def update(cls, id, new_name=None, new_email=None, new_address=None, new_url=None):
        try:
            university = cls.query.get(id)
            if university is None:
                return False, "La universidad no existe"

            if new_name is not None:
                university.name = new_name
            if new_email is not None:
                university.email = new_email
            if new_address is not None:
                university.address = new_address
            if new_url is not None: 
                university.url = new_url

            db.session.commit()
            return True, university
        except Exception as e:
            db.session.rollback()
            return False, f"Error al actualizar la universidad: {e}"
