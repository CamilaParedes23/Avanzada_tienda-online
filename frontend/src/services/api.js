import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const productoService = {
  // Obtener todos los productos
  obtenerTodos: () => api.get('/productos'),

  // Obtener producto por ID
  obtenerPorId: (id) => api.get(`/productos/${id}`),

  // Crear nuevo producto
  crear: (producto) => api.post('/productos', producto),

  // Actualizar producto
  actualizar: (id, producto) => api.put(`/productos/${id}`, producto),

  // Eliminar producto
  eliminar: (id) => api.delete(`/productos/${id}`),

  // Buscar productos por categoría
  buscarPorCategoria: (categoria) => api.get(`/productos/categoria/${categoria}`),

  // Buscar productos por nombre
  buscarPorNombre: (nombre) => api.get(`/productos/buscar?nombre=${nombre}`),

  // Obtener productos disponibles
  obtenerDisponibles: () => api.get('/productos/disponibles'),

  // Obtener todas las categorías
  obtenerCategorias: () => api.get('/productos/categorias'),

  // Obtener todas las tallas
  obtenerTallas: () => api.get('/productos/tallas'),

  // Obtener todos los colores
  obtenerColores: () => api.get('/productos/colores'),
};

export default api;
