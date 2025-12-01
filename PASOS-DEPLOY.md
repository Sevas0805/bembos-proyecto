# 🎯 Pasos Exactos para Desplegar

## PARTE 1: Backend en Railway (15 minutos)

### Paso 1: Crear cuenta Railway
1. Ve a: https://railway.app
2. Click en "Login" → "Login with GitHub"
3. Autoriza Railway

### Paso 2: Crear proyecto
1. Click en "New Project"
2. Selecciona "Deploy from GitHub repo"
3. Busca y selecciona tu repositorio: `RepoBEMBO`
4. Railway escaneará tu repo

### Paso 3: Configurar servicio
1. Railway detectará carpetas
2. Click en "Add variables" o "Settings"
3. En "Root Directory" pon: `back-bembos`
4. En "Build Command" pon: `./mvnw clean package -DskipTests`
5. En "Start Command" pon: `java -jar target/bembos-0.0.1-SNAPSHOT.war`

### Paso 4: Agregar MySQL
1. En tu proyecto, click en "+ New"
2. Selecciona "Database"
3. Click en "Add MySQL"
4. Railway creará la base de datos automáticamente

### Paso 5: Conectar backend con MySQL
1. Ve a tu servicio backend
2. Click en "Variables"
3. Railway debe tener estas variables automáticamente:
   - `MYSQL_URL`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`
   - `MYSQLDATABASE`
   - `MYSQLHOST`
   - `MYSQLPORT`

### Paso 6: Deploy
1. Click en "Deploy"
2. Espera 3-5 minutos
3. Cuando termine, click en "Settings" → "Domains"
4. Click en "Generate Domain"
5. **COPIA LA URL** (ej: `https://back-bembos-production-abcd.up.railway.app`)

✅ **Verifica que funciona**:
- Abre: `https://TU-URL-RAILWAY.up.railway.app/api/roles`
- Deberías ver JSON con los 3 roles

---

## PARTE 2: Frontend en Vercel (10 minutos)

### Paso 1: Actualizar la URL del backend
```bash
cd bembos-front
```

Edita `src/environments/environment.prod.ts`:
```typescript
export const environment = {
  production: true,
  apiUrl: 'https://TU-URL-RAILWAY.up.railway.app/api'  // ← Pega tu URL aquí
};
```

### Paso 2: Actualizar CORS en el backend

Edita `SecurityConfig.java` y actualiza la lista de orígenes permitidos.
**Esto lo haremos DESPUÉS de obtener la URL de Vercel**

### Paso 3: Crear cuenta Vercel
1. Ve a: https://vercel.com
2. Click en "Sign Up" → "Continue with GitHub"
3. Autoriza Vercel

### Paso 4: Importar proyecto
1. Click en "Add New" → "Project"
2. Busca y selecciona `RepoBEMBO`
3. Click en "Import"

### Paso 5: Configurar proyecto
```
Framework Preset: Angular
Root Directory: bembos-front
Build Command: npm run build
Output Directory: dist/bembos-front/browser
Install Command: npm install
```

### Paso 6: Deploy
1. Click en "Deploy"
2. Espera 3-5 minutos
3. Cuando termine, **COPIA LA URL** (ej: `https://bembos-front.vercel.app`)

---

## PARTE 3: Actualizar CORS (5 minutos)

### Paso 1: Actualizar SecurityConfig
En tu código local, edita:
`back-bembos/src/main/java/com/example/bembos/config/SecurityConfig.java`

Busca esta línea:
```java
configuration.setAllowedOrigins(
    Arrays.asList("http://localhost:4200", "http://localhost:5173", "http://localhost:3000")
);
```

Cámbiala por:
```java
configuration.setAllowedOrigins(
    Arrays.asList(
        "http://localhost:4200",
        "https://TU-URL-VERCEL.vercel.app"  // ← Pega tu URL de Vercel aquí
    )
);
```

### Paso 2: Hacer commit y push
```bash
git add .
git commit -m "Update CORS for production"
git push
```

Railway redesplegará automáticamente.

---

## 🧪 PROBAR LA APLICACIÓN

1. Abre tu URL de Vercel en el navegador
2. Intenta hacer login:
   - Email: `demo@bembos.com`
   - Password: `Demo1234!`
3. ✅ Si funciona, ¡LISTO!

---

## ❌ Si algo falla

### Error: "Cannot connect to backend"
- Verifica que la URL en `environment.prod.ts` sea correcta
- Asegúrate que Railway esté corriendo
- Revisa los logs en Railway

### Error: "CORS policy"
- Actualiza SecurityConfig con la URL de Vercel
- Haz push para redesplegar

### Error: "502 Bad Gateway" en Railway
- Espera 30 segundos, Railway está iniciando
- Revisa logs en Railway

### Base de datos vacía
- Railway crea tablas automáticamente en el primer inicio
- Espera 1-2 minutos

---

## 📝 URLs Finales

Guarda estas URLs:

**Frontend**: `https://_____.vercel.app`
**Backend**: `https://_____.up.railway.app`
**API**: `https://_____.up.railway.app/api`

---

¿Necesitas ayuda? Revisa los logs:
- **Railway**: Settings → Deployments → Ver logs
- **Vercel**: Deployments → Ver función logs
