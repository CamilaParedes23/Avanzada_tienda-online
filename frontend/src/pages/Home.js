import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Jumbotron, Button, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { productoService } from '../services/api';
import ProductCard from '../components/ProductCard';

function Home() {
  const [productosDestacados, setProductosDestacados] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      setLoading(true);
      const [productosResponse, categoriasResponse] = await Promise.all([
        productoService.obtenerDisponibles(),
        productoService.obtenerCategorias()
      ]);

      // Obtener solo los primeros 6 productos para mostrar como destacados
      setProductosDestacados(productosResponse.data.slice(0, 6));
      setCategorias(categoriasResponse.data);
    } catch (error) {
      console.error('Error al cargar datos:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container>
      {/* Hero Section */}
      <div className="bg-primary text-white rounded p-5 mb-5">
        <Row className="align-items-center">
          <Col md={8}>
            <h1 className="display-4">¡Bienvenido a nuestra Tienda de Ropa!</h1>
            <p className="lead">
              Descubre las últimas tendencias en moda. Calidad, estilo y los mejores precios
              en un solo lugar.
            </p>
            <Link to="/productos">
              <Button variant="light" size="lg">
                Explorar Productos
              </Button>
            </Link>
          </Col>
          <Col md={4} className="text-center">
            <div style={{ fontSize: '8rem' }}>🛍️</div>
          </Col>
        </Row>
      </div>

      {/* Categorías */}
      <Row className="mb-5">
        <Col>
          <h2 className="text-center mb-4">Categorías</h2>
          <Row>
            {categorias.map((categoria, index) => (
              <Col md={3} sm={6} key={categoria} className="mb-3">
                <Card className="text-center h-100">
                  <Card.Body>
                    <Card.Title>{categoria}</Card.Title>
                    <Link to={`/productos?categoria=${categoria}`}>
                      <Button variant="outline-primary">
                        Ver {categoria}
                      </Button>
                    </Link>
                  </Card.Body>
                </Card>
              </Col>
            ))}
          </Row>
        </Col>
      </Row>

      {/* Productos Destacados */}
      <Row>
        <Col>
          <h2 className="text-center mb-4">Productos Destacados</h2>
          {loading ? (
            <div className="text-center">
              <div className="spinner-border text-primary" role="status">
                <span className="visually-hidden">Cargando...</span>
              </div>
            </div>
          ) : (
            <Row>
              {productosDestacados.map(producto => (
                <Col lg={4} md={6} key={producto.id}>
                  <ProductCard producto={producto} />
                </Col>
              ))}
            </Row>
          )}
        </Col>
      </Row>

      {productosDestacados.length === 0 && !loading && (
        <Row>
          <Col className="text-center">
            <p className="text-muted">No hay productos disponibles en este momento.</p>
            <Link to="/admin">
              <Button variant="primary">Agregar Productos</Button>
            </Link>
          </Col>
        </Row>
      )}
    </Container>
  );
}

export default Home;
