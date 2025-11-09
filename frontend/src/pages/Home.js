import React, { useState, useEffect, useCallback } from 'react';
import { Container, Row, Col, Button, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { productoService } from '../services/api';
import ProductCard from '../components/ProductCard';
import '../App.css';

function Home() {
    const [productosDestacados, setProductosDestacados] = useState([]);
    const [categorias, setCategorias] = useState([]);
    const [loading, setLoading] = useState(true);

    const cargarDatos = useCallback(async () => {
        try {
            setLoading(true);
            const [productosResponse, categoriasResponse] = await Promise.all([
                productoService.obtenerDisponibles(),
                productoService.obtenerCategorias(),
            ]);

            const productos = productosResponse?.data || [];
            const categoriasList = categoriasResponse?.data || [];

            // Muestra los primeros 6 productos
            setProductosDestacados(productos.slice(0, 6));
            setCategorias(categoriasList);
        } catch (error) {
            console.error('Error al cargar datos:', error);
            setProductosDestacados([]);
            setCategorias([]);
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        cargarDatos();
    }, [cargarDatos]);

    return (
        <Container fluid className="home-container px-4 py-5">
            {/* ====== HERO SECTION ====== */}
            <section className="hero-section text-center mb-5 p-5 rounded shadow-sm">
                <h1 className="display-5 fw-bold mb-3">¡Bienvenido a nuestra Tienda de Ropa!</h1>
                <p className="lead text-muted mb-4">
                    Descubre las últimas tendencias en moda. Calidad, estilo y los mejores precios en un solo lugar.
                </p>
                <Link to="/productos">
                    <Button variant="dark" size="lg" className="rounded-pill px-4 py-2">
                        Explorar Productos
                    </Button>
                </Link>
                <div className="mt-4" style={{ fontSize: '6rem' }}>🛍️</div>
            </section>

            {/* ====== CATEGORÍAS ====== */}
            <section className="categories-section text-center mb-5">
                <h2 className="fw-bold mb-4">Categorías</h2>
                {categorias.length > 0 ? (
                    <div className="categories-grid">
                        {categorias.map((categoria) => (
                            <div key={categoria} className="category-card">
                                <h5 className="fw-semibold">{categoria}</h5>
                                <Link to={`/productos?categoria=${categoria}`}>
                                    <Button variant="outline-dark" className="rounded-pill mt-2">
                                        Ver {categoria}
                                    </Button>
                                </Link>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p className="text-muted">No hay categorías disponibles.</p>
                )}
            </section>

            {/* ====== PRODUCTOS DESTACADOS ====== */}
            <section className="featured-section">
                <h2 className="fw-bold text-center mb-4">Productos Destacados</h2>
                {loading ? (
                    <div className="text-center py-5">
                        <div className="spinner-border text-dark" role="status">
                            <span className="visually-hidden">Cargando...</span>
                        </div>
                    </div>
                ) : productosDestacados.length > 0 ? (
                    <div className="products-grid">
                        {productosDestacados.map((producto) => (
                            <div key={producto.id} className="product-cell">
                                <ProductCard producto={producto} />
                            </div>
                        ))}
                    </div>
                ) : (
                    <div className="text-center text-muted mt-4">
                        <p>No hay productos disponibles en este momento.</p>
                        <Link to="/admin">
                            <Button variant="dark" className="rounded-pill mt-2">Agregar Productos</Button>
                        </Link>
                    </div>
                )}
            </section>
        </Container>
    );
}

export default Home;
