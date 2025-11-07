# Frontend - Tienda de Ropa

Este es el frontend de la tienda de ropa desarrollado con React y Bootstrap.

## 🚀 Configuración Rápida

### Pre-requisitos
- Node.js 18+ instalado
- Backend ejecutándose en http://localhost:8080
- Base de datos MySQL configurada con XAMPP

## Available Scripts

En este directorio, puedes ejecutar:

### `npm start`

Inicia la aplicación React en modo desarrollo.\
Abre [http://localhost:3000](http://localhost:3000) para verla en tu navegador.

**IMPORTANTE**: Asegúrate de que el backend esté ejecutándose en http://localhost:8080 antes de iniciar el frontend.

La página se recargará automáticamente cuando hagas cambios.\
También verás errores de lint en la consola.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

## 🛠️ Configuración Específica para XAMPP

### 1. Configurar Base de Datos en XAMPP

1. Inicia **XAMPP Control Panel**
2. Arranca **Apache** y **MySQL**
3. Ve a http://localhost/phpmyadmin
4. Crea la base de datos:
   ```sql
   CREATE DATABASE tienda_ropa;
   ```
5. Ejecuta el archivo de datos de ejemplo (opcional):
   - Ve al directorio raíz del proyecto
   - Ejecuta `datos_ejemplo.sql` en phpMyAdmin

### 2. Configurar Backend

Antes de iniciar el frontend, configura el backend:

1. Ve al directorio raíz del proyecto: `C:\Users\usuario\IdeaProjects\tienda_ropa`
2. Edita `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tienda_ropa
   spring.datasource.username=root
   spring.datasource.password=
   ```
   (Deja password vacío si no has configurado contraseña en XAMPP)

3. Inicia el backend:
   ```bash
   # En el directorio raíz del proyecto
   gradlew.bat bootRun
   ```

### 3. Iniciar Frontend

Una vez que el backend esté ejecutándose:

1. Abre nueva terminal en el directorio `frontend`
2. Instala dependencias (primera vez):
   ```bash
   npm install
   ```
3. Inicia el servidor de desarrollo:
   ```bash
   npm start
   ```

### 🎯 URLs Disponibles

Una vez todo esté ejecutándose:
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api/productos
- **Admin Panel**: http://localhost:3000/admin
- **phpMyAdmin**: http://localhost/phpmyadmin

### 🐛 Troubleshooting

#### Si el frontend no conecta con el backend:
1. Verifica que el backend esté en http://localhost:8080
2. Abre las herramientas de desarrollador (F12) y revisa errores en la consola

#### Si hay errores de CORS:
- El backend ya tiene configuración CORS para React
- Verifica que las URLs en `src/services/api.js` sean correctas

#### Si la base de datos no conecta:
1. Verifica que MySQL esté ejecutándose en XAMPP
2. Confirma que la base de datos `tienda_ropa` existe
3. Revisa las credenciales en `application.properties`

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)
