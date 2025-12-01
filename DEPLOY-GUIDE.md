# 🚀 Guía de Despliegue - Proyecto Bembos

## Backend en Railway

### 1. Crear cuenta en Railway
- Ve a https://railway.app
- Regístrate con GitHub

### 2. Crear nuevo proyecto
1. Click en "New Project"
2. Selecciona "Deploy from GitHub repo"
3. Conecta tu repositorio `RepoBEMBO`
4. Selecciona la carpeta `back-bembos`

### 3. Agregar MySQL
1. En tu proyecto Railway, click en "+ New"
2. Selecciona "Database" → "MySQL"
3. Railway creará automáticamente las variables:
   - `MYSQL_URL`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`
   - `MYSQLDATABASE`

### 4. Configurar variables de entorno del backend
En el servicio del backend, agrega estas variables:
```
PORT=8080
MYSQL_URL=(automático)
MYSQLUSER=(automático)
MYSQLPASSWORD=(automático)
```

### 5. Deploy
- Railway detectará automáticamente que es Java/Maven
- Compilará y desplegará el backend
- Te dará una URL como: `https://tu-proyecto.up.railway.app`

⚠️ **IMPORTANTE**: Guarda esta URL, la necesitarás para el frontend.

---

## Frontend en Vercel

### 1. Actualizar URL del backend
Antes de desplegar, actualiza `src/environments/environment.prod.ts`:
```typescript
export const environment = {
  production: true,
  apiUrl: 'https://TU-URL-DE-RAILWAY.up.railway.app/api'
};
```

### 2. Compilar el proyecto
```bash
cd bembos-front
npm install
npm run build
```

### 3. Desplegar en Vercel
Opción A - Desde GitHub:
1. Ve a https://vercel.com
2. Importa tu repositorio
3. Framework Preset: Angular
4. Root Directory: `bembos-front`
5. Build Command: `npm run build`
6. Output Directory: `dist/bembos-front/browser`

Opción B - Desde CLI:
```bash
npm install -g vercel
vercel --prod
```

### 4. Actualizar CORS en Railway
Después del deploy de Vercel, actualiza el SecurityConfig con tu URL:
```java
configuration.setAllowedOrigins(
    Arrays.asList(
        "http://localhost:4200",
        "https://tu-app.vercel.app"  // ← Tu URL de Vercel
    )
);
```

---

## 📋 Checklist Final

Backend Railway:
- [ ] Proyecto creado
- [ ] MySQL agregado
- [ ] Variables configuradas
- [ ] Deploy exitoso
- [ ] URL guardada

Frontend Vercel:
- [ ] environment.prod.ts actualizado
- [ ] Proyecto compilado
- [ ] Deploy exitoso
- [ ] CORS actualizado en backend

## 🧪 Probar la aplicación

1. Abre tu URL de Vercel
2. Intenta hacer login:
   - Email: `demo@bembos.com`
   - Password: `Demo1234!`
3. Verifica que carguen ingredientes y hamburguesas
4. Prueba el carrito

## ⚠️ Problemas Comunes

**Error CORS**: Asegúrate de actualizar SecurityConfig con la URL de Vercel.

**Base de datos vacía**: Railway creará las tablas automáticamente en el primer inicio.

**502/503 en Railway**: El servicio puede tardar ~30 segundos en el primer inicio.

---

## 🔗 URLs de tu proyecto

- **Frontend (Vercel)**: `https://_____.vercel.app`
- **Backend (Railway)**: `https://_____.up.railway.app`
- **API Docs**: `https://_____.up.railway.app/api`
