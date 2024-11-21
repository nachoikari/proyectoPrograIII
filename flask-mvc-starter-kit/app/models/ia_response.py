from . import db
class IaResponse(db.Model):
    __tablename__ = 'ia_response'
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    quality = db.Column(db.Integer, nullable=False)
    text = db.Column(db.String(500), nullable=False)

    def __repr__(self):
        return f"<IaResponse(id={self.id}, quality={self.quality}, text='{self.text}')>"