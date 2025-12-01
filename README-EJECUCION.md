# 🍔 Proyecto Bembos - Guía de Ejecución

## ✅ Conexión Frontend-Backend COMPLETADA

El frontend ahora está conectado al backend real y ya no usa datos simulados (mocks).

## 📋 Requisitos Previos

1. **MySQL** corriendo en puerto **3307**
2. **Java 21** instalado
3. **Node.js** instalado
4. Base de datos `bembosbd` creada

## 🚀 Pasos para Ejecutar

### 1️⃣ Preparar Base de Datos

Abre MySQL Workbench y ejecuta:

```sql
CREATE DATABASE IF NOT EXISTS bembosbd;
```

### 2️⃣ Arrancar Backend

Abre una terminal PowerShell en la carpeta del proyecto y ejecuta:

```powershell
cd "C:\Users\LENOVO\Downloads\Proyecto-COMPLETO\Proyecto-COMPLETO\back-bembos"
.\mvnw.cmd spring-boot:run
```

**Espera a ver este mensaje:**
```
Started BembosApplication in X seconds
```

El backend:
- ✅ Creará automáticamente todas las tablas
- ✅ Cargará datos iniciales (ingredientes, roles, usuario demo)
- ✅ Estará disponible en `http://localhost:8080`

**Usuario de prueba creado:**
- Email: `demo@bembos.com`
- Password: `Demo1234!`

### 3️⃣ Arrancar Frontend

Abre **otra** terminal PowerShell y ejecuta:

```powershell
cd "C:\Users\LENOVO\Downloads\Proyecto-COMPLETO\Proyecto-COMPLETO\bembos-front"
npm start
```

El frontend estará en: `http://localhost:4200`

## 🔗 Endpoints del Backend

### Autenticación
- **Login:** `POST http://localhost:8080/api/usuario/authenticate`
  - Params: `email`, `password`
- **Registro:** `POST http://localhost:8080/api/usuario/register`
  - Params: `nombre`, `email`, `password`

### Ingredientes
- **Listar todos:** `GET http://localhost:8080/api/ingredientes`
- **Obtener por ID:** `GET http://localhost:8080/api/ingredientes/{id}`
- **Crear:** `POST http://localhost:8080/api/ingredientes`
- **Actualizar:** `PUT http://localhost:8080/api/ingredientes/{id}`
- **Eliminar:** `DELETE http://localhost:8080/api/ingredientes/{id}`

### Hamburguesas
- **Listar todas:** `GET http://localhost:8080/api/hamburguesas`
- **Populares:** `GET http://localhost:8080/api/hamburguesas/populares`
- **Por usuario:** `GET http://localhost:8080/api/hamburguesas/usuario/{id}`
- **Crear:** `POST http://localhost:8080/api/hamburguesas`

## 🧪 Probar con Postman

### 1. Login
```
POST http://localhost:8080/api/usuario/authenticate?email=demo@bembos.com&password=Demo1234!
```

Respuesta:
```json
{
  "token": "eyJhbGci...",
  "role": "ROLE_CLIENTE",
  "user": { ... }
}
```

### 2. Listar Ingredientes
```
GET http://localhost:8080/api/ingredientes
```

### 3. Crear Ingrediente (con JWT)
```
POST http://localhost:8080/api/ingredientes
Headers:
  Content-Type: application/json
  Authorization: Bearer <tu-token>
Body:
{
  "nombre": "Queso Mozzarella",
  "precio": 2.5,
  "descripcion": "Queso italiano",
  "tipoIngredienteId": 3
}
```

## 📊 Ver Datos en MySQL Workbench

```sql
USE bembosbd;

-- Ver todos los ingredientes
SELECT * FROM ingredientes;

-- Ver tipos de ingredientes
SELECT * FROM tipos_ingrediente;

-- Ver usuarios
SELECT * FROM usuarios;

-- Ver roles
SELECT * FROM roles;
```

## 🔧 Cambios Realizados

### Backend
- ✅ Puerto MySQL corregido: `3307`
- ✅ Contraseña MySQL configurada: `root`
- ✅ Datos iniciales agregados (`DataInitializer.java`)
  - 6 tipos de ingredientes
  - 24 ingredientes precargados
  - 3 roles (CLIENTE, BURGERBUILDER, ADMINISTRADOR)
  - Usuario demo

### Frontend
- ✅ Archivos de entorno creados (`environment.ts`, `environment.prod.ts`)
- ✅ `auth.service.ts` conectado a `/api/usuario/authenticate` y `/register`
- ✅ `ingredients.service.ts` conectado a `/api/ingredientes`
- ✅ `orders.service.ts` conectado a `/api/hamburguesas`
- ✅ Interceptor JWT ya configurado en `app.config.ts`
- ✅ Ya no usa datos mock

## ❌ Solución de Problemas

### Backend no arranca
- ✅ Verifica que MySQL esté corriendo: `Get-Service MySQL80`
- ✅ Verifica el puerto: `netstat -ano | Select-String "3307"`
- ✅ Revisa `application.properties` (usuario: `root`, password: `root`, puerto: `3307`)

### Frontend no conecta
- ✅ Verifica que el backend esté corriendo en `http://localhost:8080`
- ✅ Revisa la consola del navegador (F12) para ver errores
- ✅ Verifica CORS (ya está permitido en `SecurityConfig.java`)

### Error de autenticación
- ✅ Usa el usuario de prueba: `demo@bembos.com` / `demo1234`
- ✅ El token JWT se guarda automáticamente en `localStorage`

## 📦 Próximos Pasos

- [ ] Agregar más ingredientes desde Postman o la UI
- [ ] Crear hamburguesas personalizadas
- [ ] Publicar hamburguesas en el ranking
- [ ] Desplegar en producción (Render, Railway, etc.)

---

**¡Listo!** Tu aplicación full-stack está conectada y funcionando. 🎉
