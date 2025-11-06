# 🚀 GUÍA RÁPIDA DE INICIO - Tienda de Ropa

## ✅ Requisitos Previos
- [x] XAMPP instalado
- [x] Node.js 18+ instalado  
- [x] Java 17+ instalado

## 🏃‍♂️ Inicio Rápido (3 opciones)

### Opción 1: Script Automático (Recomendado)
```bash
# Ejecuta este archivo y sigue las instrucciones
iniciar_con_xampp.bat
```

### Opción 2: Paso a Paso Manual

#### 1. Preparar XAMPP
1. Abre **XAMPP Control Panel**
2. Inicia **Apache** y **MySQL** 
3. Ve a http://localhost/phpmyadmin
4. Ejecuta: `CREATE DATABASE tienda_ropa;`

#### 2. Iniciar Backend
```bash
# En el directorio raíz del proyecto
gradlew.bat bootRun
```

#### 3. Iniciar Frontend
```bash
# En nueva terminal, directorio frontend/
cd frontend
npm install    # Solo la primera vez
npm start
```

### Opción 3: Con Docker (Alternativa)
```bash
# Si prefieres no usar XAMPP
docker-compose up -d --build
```

## 🌐 URLs del Proyecto

Una vez iniciado:
- **🏠 Frontend**: http://localhost:3000
- **⚙️ Backend API**: http://localhost:8080/api/productos  
- **👨‍💼 Admin Panel**: http://localhost:3000/admin
- **🗄️ Base de Datos**: http://localhost/phpmyadmin

## 📊 Datos de Ejemplo

Para poblar la base de datos con productos de ejemplo:
1. Ve a phpMyAdmin
2. Selecciona la base de datos `tienda_ropa`  
3. Importa el archivo `datos_ejemplo.sql`

## 🐛 Solución de Problemas Comunes

### ❌ "Backend no inicia"
- Verifica que MySQL esté ejecutándose en XAMPP
- Confirma que existe la base de datos `tienda_ropa`
- Revisa las credenciales en `application.properties`

### ❌ "Frontend no conecta con Backend"  
- Asegúrate que el backend esté en http://localhost:8080
- Verifica que no haya errores CORS (ya configurado)

### ❌ "Puerto ocupado"
- Backend usa puerto 8080, Frontend usa 3000
- Si están ocupados, cambia los puertos en la configuración

## 📚 Estructura del Proyecto

```
tienda_ropa/
├── src/main/java/          # Backend Spring Boot
├── frontend/               # Frontend React
├── iniciar_con_xampp.bat  # Script de inicio para XAMPP
├── deploy.bat             # Script para deployment con Docker
└── datos_ejemplo.sql      # Datos de ejemplo para la DB
```

## 🎯 Próximos Pasos

1. **Agregar productos**: Ve a http://localhost:3000/admin
2. **Explorar catálogo**: Navega por http://localhost:3000/productos  
3. **Revisar API**: Consulta http://localhost:8080/api/productos
4. **Gestionar clientes**: Usar endpoints `/api/clientes`
5. **Crear pedidos**: Usar endpoints `/api/pedidos`

---

💡 **Tip**: Usa `iniciar_con_xampp.bat` para una configuración guiada paso a paso.
