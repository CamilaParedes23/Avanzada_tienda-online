@echo off
color 0a
echo.
echo  ██████████████████████████████████████████████████
echo  ██                                              ██
echo  ██          🏪 TIENDA DE ROPA                   ██
echo  ██          Iniciar Backend y Frontend          ██
echo  ██                                              ██
echo  ██████████████████████████████████████████████████
echo.

echo ✅ PREREQUISITOS:
echo    - XAMPP ejecutándose (Apache y MySQL)
echo    - Base de datos 'tienda_ropa' creada
echo.

echo 🔍 Verificando XAMPP...
timeout /t 2 /nobreak > nul

echo.
echo 🚀 INICIANDO BACKEND (Spring Boot)...
echo    Puerto: 8080
echo    URL API: http://localhost:8080/api/productos
echo.

start "🔧 Backend - Tienda de Ropa" cmd /k "echo Iniciando Spring Boot... && gradlew.bat bootRun"

echo ⏳ Esperando que el backend inicie (30 segundos)...
timeout /t 30 /nobreak > nul

echo.
echo 🌐 INICIANDO FRONTEND (React)...
echo    Puerto: 3000
echo    URL: http://localhost:3000
echo.

cd frontend
start "🏪 Frontend - Tienda de Ropa" cmd /k "echo Iniciando React... && npm start"
cd ..

echo.
echo ✅ ¡AMBOS SERVICIOS INICIADOS!
echo.
echo 📱 URLS DISPONIBLES:
echo    🏠 Tienda:        http://localhost:3000
echo    👨‍💼 Admin Panel:   http://localhost:3000/admin
echo    ⚙️ API Backend:    http://localhost:8080/api/productos
echo    🗄️ phpMyAdmin:     http://localhost/phpmyadmin
echo.
echo 🛑 PARA DETENER:
echo    - Cierra las ventanas de Backend y Frontend
echo    - O presiona Ctrl+C en cada ventana
echo.
echo 💡 TIP: Si algo no funciona, usa: iniciar_con_xampp.bat
echo.
pause
