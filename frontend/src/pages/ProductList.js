import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Form, Button, Card } from 'react-bootstrap';
import { productoService } from '../services/api';
import ProductCard from '../components/ProductCard';

function ProductList() {
  const [productos, setProductos] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [tallas, setTallas] = useState([]);
  const [colores, setColores] = useState([]);
  const [loading, setLoading] = useState(true);

  // Filtros
  const [busqueda, setBusqueda] = useState('');
  const [categoriaSeleccionada, setCategoriaSeleccionada] = useState('');
  const [tallaSeleccionada, setTallaSeleccionada] = useState('');
  const [colorSeleccionado, setColorSeleccionado] = useState('');

  useEffect(() => {
    cargarDatos();
  }, []);

  useEffect(() => {
    filtrarProductos();
  }, [busqueda, categoriaSeleccionada, tallaSeleccionada, colorSeleccionado]);

  const cargarDatos = async () => {
    try {
      setLoading(true);
      const [productosResponse, categoriasResponse, tallasResponse, coloresResponse] = await Promise.all([
        productoService.obtenerTodos(),
        productoService.obtenerCategorias(),
        productoService.obtenerTallas(),
        productoService.obtenerColores()
      ]);

      setProductos(productosResponse.data);
      setCategorias(categoriasResponse.data);
      setTallas(tallasResponse.data);
      setColores(coloresResponse.data);
    } catch (error) {
      console.error('Error al cargar productos:', error);
    } finally {
      setLoading(false);
    }
  };

  const filtrarProductos = async () => {
    try {
      let productosResponse;

      if (busqueda) {
        productosResponse = await productoService.buscarPorNombre(busqueda);
      } else if (categoriaSeleccionada) {
        productosResponse = await productoService.buscarPorCategoria(categoriaSeleccionada);
      } else {
        productosResponse = await productoService.obtenerTodos();
      }

      let productosFiltrados = productosResponse.data;

      // Aplicar filtros adicionales
      if (tallaSeleccionada) {
        productosFiltrados = productosFiltrados.filter(p => p.talla === tallaSeleccionada);
      }

      if (colorSeleccionado) {
        productosFiltrados = productosFiltrados.filter(p => p.color === colorSeleccionado);
      }

      setProductos(productosFiltrados);
    } catch (error) {
      console.error('Error al filtrar productos:', error);
    }
  };

  const limpiarFiltros = () => {
    setBusqueda('');
    setCategoriaSeleccionada('');
    setTallaSeleccionada('');
    setColorSeleccionado('');
  };

  return (
    <Container>
      <Row>
        <Col md={3}>
          <Card className="filter-sidebar mb-4">
            <Card.Body>
              <h5>Filtros</h5>

              {/* Búsqueda */}
              <Form.Group className="mb-3">
                <Form.Label>Buscar</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Nombre del producto..."
                  value={busqueda}
                  onChange={(e) => setBusqueda(e.target.value)}
                />
              </Form.Group>

              {/* Categoría */}
              <Form.Group className="mb-3">
                <Form.Label>Categoría</Form.Label>
                <Form.Select
                  value={categoriaSeleccionada}
                  onChange={(e) => setCategoriaSeleccionada(e.target.value)}
                >
                  <option value="">Todas las categorías</option>
                  {categorias.map(categoria => (
                    <option key={categoria} value={categoria}>{categoria}</option>
                  ))}
                </Form.Select>
              </Form.Group>

              {/* Talla */}
              <Form.Group className="mb-3">
                <Form.Label>Talla</Form.Label>
                <Form.Select
                  value={tallaSeleccionada}
                  onChange={(e) => setTallaSeleccionada(e.target.value)}
                >
                  <option value="">Todas las tallas</option>
                  {tallas.map(talla => (
                    <option key={talla} value={talla}>{talla}</option>
                  ))}
                </Form.Select>
              </Form.Group>

              {/* Color */}
              <Form.Group className="mb-3">
                <Form.Label>Color</Form.Label>
                <Form.Select
                  value={colorSeleccionado}
                  onChange={(e) => setColorSeleccionado(e.target.value)}
                >
                  <option value="">Todos los colores</option>
                  {colores.map(color => (
                    <option key={color} value={color}>{color}</option>
                  ))}
                </Form.Select>
              </Form.Group>

              <Button variant="outline-secondary" onClick={limpiarFiltros} className="w-100">
                Limpiar Filtros
              </Button>
            </Card.Body>
          </Card>
        </Col>

        <Col md={9}>
          <div className="d-flex justify-content-between align-items-center mb-4">
            <h2>Productos ({productos.length})</h2>
          </div>

          {loading ? (
            <div className="text-center">
              <div className="spinner-border text-primary" role="status">
                <span className="visually-hidden">Cargando...</span>
              </div>
            </div>
          ) : (
            <Row>
              {productos.map(producto => (
                <Col lg={4} md={6} key={producto.id}>
                  <ProductCard producto={producto} />
                </Col>
              ))}
            </Row>
          )}

          {productos.length === 0 && !loading && (
            <div className="text-center">
              <p className="text-muted">No se encontraron productos con los filtros seleccionados.</p>
            </div>
          )}
        </Col>
      </Row>
    </Container>
  );
}

export default ProductList;
