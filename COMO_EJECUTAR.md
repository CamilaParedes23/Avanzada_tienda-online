# 🚀 CÓMO EJECUTAR EL PROYECTO

## 📋 Resumen Rápido

Para ejecutar tu tienda de ropa necesitas 3 cosas ejecutándose:
1. **XAMPP** (Base de datos)
2. **Backend** (Spring Boot - Puerto 8080)
3. **Frontend** (React - Puerto 3000)

---

## 🎯 MÉTODO RÁPIDO

### 1. Ejecuta este comando:
```bash
ejecutar.bat
```
✅ Este script hace todo automáticamente

---

## 🔧 MÉTODO MANUAL (Paso a Paso)

### PASO 1: Preparar XAMPP 🗄️
1. Abre **XAMPP Control Panel**
2. Haz clic en **Start** junto a:
   - ✅ **Apache** 
   - ✅ **MySQL**
3. Ambos deben estar en **verde**

### PASO 2: Crear Base de Datos 📊
1. Ve a: http://localhost/phpmyadmin
2. Haz clic en **"Nuevo"** o **"New"**
3. Nombre: `tienda_ropa`
4. Haz clic en **"Crear"**

### PASO 3: Ejecutar Backend ⚙️
```bash
# Abre CMD o PowerShell en:
# C:\Users\usuario\IdeaProjects\tienda_ropa

# Ejecuta:
gradlew.bat bootRun
```

**✅ Sabrás que funciona cuando veas:**
```
Started TiendaRopaApplication in 15.234 seconds
```

### PASO 4: Ejecutar Frontend 🌐
```bash
# Abre NUEVA terminal en el directorio frontend:
cd frontend

# Primera vez solamente:
npm install

# Ejecuta siempre:
npm start
```

**✅ Se abrirá automáticamente:** http://localhost:3000

---

## 🎯 URLs del Proyecto

Una vez todo ejecutándose:

| Servicio | URL | Descripción |
|----------|-----|-------------|
| 🏪 **Tienda** | http://localhost:3000 | Página principal de la tienda |
| 👨‍💼 **Admin** | http://localhost:3000/admin | Panel para agregar productos |
| ⚙️ **API** | http://localhost:8080/api/productos | Backend REST API |
| 🗄️ **Base de Datos** | http://localhost/phpmyadmin | Administrar MySQL |

---

## 🐛 ¿Algo no funciona?

### ❌ Backend no inicia
```bash
# Verifica Java:
java -version

# Debe mostrar Java 17 o superior
```

### ❌ Frontend no inicia
```bash
# Verifica Node.js:
node --version
npm --version

# Si no tienes Node.js, descarga desde: https://nodejs.org
```

### ❌ Error de conexión a base de datos
1. ✅ XAMPP MySQL está iniciado (verde)
2. ✅ Base de datos `tienda_ropa` existe
3. ✅ Credenciales en `application.properties` son correctas

### ❌ Página en blanco en el frontend
1. ✅ Backend está ejecutándose (puerto 8080)
2. ✅ No hay errores en la consola del navegador (F12)

---

## 🎉 ¡Listo para Usar!

1. **Ve a:** http://localhost:3000
2. **Agrega productos:** http://localhost:3000/admin
3. **Explora la tienda**
4. **Revisa la API:** http://localhost:8080/api/productos

---

## 🛑 Para Detener

1. **Backend:** Presiona `Ctrl + C` en su terminal
2. **Frontend:** Presiona `Ctrl + C` en su terminal  
3. **XAMPP:** Haz clic en "Stop" para Apache y MySQL
