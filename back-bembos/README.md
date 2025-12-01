# 🍔 Bembos - Aplicación Web Completa

## 🚀 Despliegue en Railway (Gratis)

### Paso 1: Crear cuenta
1. Ve a https://railway.app/
2. Regístrate con GitHub

### Paso 2: Subir código a GitHub
```bash
cd back-bembos
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/bembos-backend.git
git push -u origin main
```

### Paso 3: Desplegar en Railway
1. En Railway, haz clic en "New Project"
2. Selecciona "Deploy from GitHub repo"
3. Elige tu repositorio `bembos-backend`
4. Railway detectará automáticamente que es un proyecto Spring Boot

### Paso 4: Agregar base de datos MySQL
1. En tu proyecto de Railway, haz clic en "+ New"
2. Selecciona "Database" → "MySQL"
3. Railway creará automáticamente las variables:
   - `DATABASE_URL`
   - `DB_USER`  
   - `DB_PASSWORD`

### Paso 5: Obtener URL pública
1. Ve a "Settings" de tu servicio
2. En "Networking", haz clic en "Generate Domain"
3. Recibirás una URL como: `https://tu-app.up.railway.app`

## 📱 Acceso desde cualquier dispositivo
Una vez desplegado, comparte el link: `https://tu-app.up.railway.app`

---

## 🖥️ Ejecución local

### Backend
```bash
cd back-bembos
./mvnw spring-boot:run
```
Accede en: http://localhost:8080

### Frontend (desarrollo)
```bash
cd bembos-front
npm install
npm start
```
Accede en: http://localhost:4200

---

## 👤 Usuarios de prueba
- **Email:** demo@bembos.com  
- **Contraseña:** Demo1234!

## 📝 Endpoints principales
- `POST /api/usuario/register` - Registro de usuario
- `POST /api/usuario/authenticate` - Login
- `GET /api/ingredientes` - Listar ingredientes
- `POST /api/hamburguesas` - Crear hamburguesa personalizada

## 🛠️ Tecnologías
- **Backend:** Spring Boot 3.5.6, Java 21, MySQL, JWT
- **Frontend:** Angular 20, TypeScript, SASS
- **Despliegue:** Railway, Tomcat embebido
