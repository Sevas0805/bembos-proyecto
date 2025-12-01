# Script para arrancar el proyecto Bembos completo
# Ejecuta: .\START-PROJECT.ps1

Write-Host "🍔 Iniciando Proyecto Bembos..." -ForegroundColor Green
Write-Host ""

# Verificar MySQL
Write-Host "1️⃣ Verificando MySQL..." -ForegroundColor Yellow
$mysqlService = Get-Service -Name "MySQL80" -ErrorAction SilentlyContinue
if ($mysqlService -and $mysqlService.Status -eq "Running") {
    Write-Host "   ✅ MySQL está corriendo" -ForegroundColor Green
} else {
    Write-Host "   ❌ MySQL no está corriendo. Inícialo desde MySQL Workbench o Servicios." -ForegroundColor Red
    exit 1
}

# Verificar puerto 3307
$mysqlPort = netstat -ano | Select-String "3307" | Select-Object -First 1
if ($mysqlPort) {
    Write-Host "   ✅ MySQL escuchando en puerto 3307" -ForegroundColor Green
} else {
    Write-Host "   ⚠️  Advertencia: No se detectó MySQL en puerto 3307" -ForegroundColor Yellow
}

Write-Host ""

# Arrancar Backend
Write-Host "2️⃣ Arrancando Backend (Spring Boot)..." -ForegroundColor Yellow
$backendPath = "C:\Users\LENOVO\Downloads\Proyecto-COMPLETO\Proyecto-COMPLETO\back-bembos"

if (Test-Path $backendPath) {
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$backendPath'; Write-Host '🚀 Iniciando Backend...' -ForegroundColor Cyan; .\mvnw.cmd spring-boot:run"
    Write-Host "   ✅ Backend iniciándose en nueva ventana..." -ForegroundColor Green
    Write-Host "   📍 URL: http://localhost:8080" -ForegroundColor Cyan
} else {
    Write-Host "   ❌ No se encontró el directorio del backend" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "   ⏳ Esperando 15 segundos para que el backend arranque..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

# Arrancar Frontend
Write-Host ""
Write-Host "3️⃣ Arrancando Frontend (Angular)..." -ForegroundColor Yellow
$frontendPath = "C:\Users\LENOVO\Downloads\Proyecto-COMPLETO\Proyecto-COMPLETO\bembos-front"

if (Test-Path $frontendPath) {
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$frontendPath'; Write-Host '🚀 Iniciando Frontend...' -ForegroundColor Cyan; npm start"
    Write-Host "   ✅ Frontend iniciándose en nueva ventana..." -ForegroundColor Green
    Write-Host "   📍 URL: http://localhost:4200" -ForegroundColor Cyan
} else {
    Write-Host "   ❌ No se encontró el directorio del frontend" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "===========================================
" -ForegroundColor Green
Write-Host "✅ Proyecto iniciado correctamente" -ForegroundColor Green
Write-Host "===========================================
" -ForegroundColor Green
Write-Host ""
Write-Host "📝 Usuario de prueba:" -ForegroundColor Cyan
Write-Host "   Email: demo@bembos.com" -ForegroundColor White
Write-Host "   Password: Demo1234!" -ForegroundColor White
Write-Host ""
Write-Host "🌐 URLs:" -ForegroundColor Cyan
Write-Host "   Backend API: http://localhost:8080" -ForegroundColor White
Write-Host "   Frontend:    http://localhost:4200" -ForegroundColor White
Write-Host ""
Write-Host "📖 Lee README-EJECUCION.md para más información" -ForegroundColor Yellow
Write-Host ""
