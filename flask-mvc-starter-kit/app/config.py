# Statement for enabling the development environment
DEBUG = True

# Define the application directory
import os
BASE_DIR = os.path.abspath(os.path.dirname(__file__))  

# Conector para MySQL en entorno local
connector = "mysql+mysqlconnector"
endpoint = "localhost"  # Localhost ya configurado
user = "nachoDesktop"
passwd = "NAcho_20040108"
database = "db_proyecto"

# SQLAlchemy configuration
SQLALCHEMY_TRACK_MODIFICATIONS = False
DATABASE_CONNECT_OPTIONS = {}

# URI de conexión a la base de datos local MySQL
SQLALCHEMY_DATABASE_URI = "{}://{}:{}@{}:3306/{}".format(connector, user, passwd, endpoint, database)
DATABASE_CONNECT_OPTIONS = {}

# Application threads
THREADS_PER_PAGE = 2

# Enable protection against Cross-site Request Forgery (CSRF)
CSRF_ENABLED = True

# Secure keys
CSRF_SESSION_KEY = "secret"
SECRET_KEY = "secret"
