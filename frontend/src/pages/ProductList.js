import React, { useState, useEffect, useCallback } from 'react';
import { Container, Col, Form, Button, Card } from 'react-bootstrap';
import { productoService } from '../services/api';
import ProductCard from '../components/ProductCard';
import '../App.css';

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

    const cargarDatos = useCallback(async () => {
        try {
            setLoading(true);
            const [
                productosResponse,
                categoriasResponse,
                tallasResponse,
                coloresResponse,
            ] = await Promise.all([
                productoService.obtenerTodos(),
                productoService.obtenerCategorias(),
                productoService.obtenerTallas(),
                productoService.obtenerColores(),
            ]);

            setProductos(productosResponse?.data || []);
            setCategorias(categoriasResponse?.data || []);
            setTallas(tallasResponse?.data || []);
            setColores(coloresResponse?.data || []);
        } catch (error) {
            console.error('Error al cargar productos:', error);
            setProductos([]);
        } finally {
            setLoading(false);
        }
    }, []);

    const filtrarProductos = useCallback(async () => {
        try {
            let productosResponse;

            if (busqueda) {
                productosResponse = await productoService.buscarPorNombre(busqueda);
            } else if (categoriaSeleccionada) {
                productosResponse = await productoService.buscarPorCategoria(categoriaSeleccionada);
            } else {
                productosResponse = await productoService.obtenerTodos();
            }

            let productosFiltrados = productosResponse?.data || [];

            if (tallaSeleccionada) {
                productosFiltrados = productosFiltrados.filter(
                    (p) => p.talla === tallaSeleccionada
                );
            }

            if (colorSeleccionado) {
                productosFiltrados = productosFiltrados.filter(
                    (p) => p.color === colorSeleccionado
                );
            }

            setProductos(productosFiltrados);
        } catch (error) {
            console.error('Error al filtrar productos:', error);
        }
    }, [busqueda, categoriaSeleccionada, tallaSeleccionada, colorSeleccionado]);

    useEffect(() => {
        cargarDatos();
    }, [cargarDatos]);

    useEffect(() => {
        filtrarProductos();
    }, [filtrarProductos]);

    const limpiarFiltros = () => {
        setBusqueda('');
        setCategoriaSeleccionada('');
        setTallaSeleccionada('');
        setColorSeleccionado('');
    };

    return (
        <Container fluid className="product-list-container py-5">
            {/* ======= CATEGORÍAS ======= */}
            {categorias.length > 0 && (
                <div className="categories-section mb-5 text-center">
                    <h2 className="fw-bold mb-4">Categorías</h2>
                    <div className="d-flex flex-wrap justify-content-center gap-3">
                        {categorias.map((categoria) => (
                            <Button
                                key={categoria}
                                variant="outline-dark"
                                className="rounded-pill px-4 py-2 category-btn"
                                onClick={() => setCategoriaSeleccionada(categoria)}
                            >
                                {categoria}
                            </Button>
                        ))}
                    </div>
                </div>
            )}

            <div className="shop-layout">
                {/* ====== SIDEBAR ====== */}
                <Col xs={12} md={3} className="sidebar mb-4">
                    <Card className="filter-sidebar shadow-sm border-0 p-3">
                        <Card.Body>
                            <h5 className="fw-bold mb-4 text-center">Filtros</h5>

                            {/* Buscar */}
                            <Form.Group className="mb-3">
                                <Form.Label>Buscar</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Nombre del producto..."
                                    value={busqueda}
                                    onChange={(e) => setBusqueda(e.target.value)}
                                />
                            </Form.Group>

                            {/* Talla */}
                            <Form.Group className="mb-3">
                                <Form.Label>Talla</Form.Label>
                                <Form.Select
                                    value={tallaSeleccionada}
                                    onChange={(e) => setTallaSeleccionada(e.target.value)}
                                >
                                    <option value="">Todas las tallas</option>
                                    {tallas.map((talla) => (
                                        <option key={talla} value={talla}>
                                            {talla}
                                        </option>
                                    ))}
                                </Form.Select>
                            </Form.Group>

                            {/* Color */}
                            <Form.Group className="mb-4">
                                <Form.Label>Color</Form.Label>
                                <Form.Select
                                    value={colorSeleccionado}
                                    onChange={(e) => setColorSeleccionado(e.target.value)}
                                >
                                    <option value="">Todos los colores</option>
                                    {colores.map((color) => (
                                        <option key={color} value={color}>
                                            {color}
                                        </option>
                                    ))}
                                </Form.Select>
                            </Form.Group>

                            <Button
                                variant="dark"
                                onClick={limpiarFiltros}
                                className="w-100 rounded-pill"
                            >
                                Limpiar Filtros
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>

                {/* ====== PRODUCTOS ====== */}
                <div className="products-area">
                    <h2 className="fw-bold mb-4 text-center text-md-start">
                        Productos ({productos.length})
                    </h2>

                    {loading ? (
                        <div className="text-center py-5">
                            <div className="spinner-border text-dark" role="status">
                                <span className="visually-hidden">Cargando...</span>
                            </div>
                        </div>
                    ) : productos.length > 0 ? (
                        <div className="products-grid">
                            {productos.map((producto) => (
                                <div key={producto.id} className="product-cell">
                                    <ProductCard producto={producto} />
                                </div>
                            ))}
                        </div>
                    ) : (
                        <div className="text-center text-muted mt-4">
                            <p>No se encontraron productos con los filtros seleccionados.</p>
                        </div>
                    )}
                </div>
            </div>
        </Container>
    );
}

export default ProductList;
