import React, { useState } from 'react';
import { Card, Button, Badge } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useCart } from '../context/CartContext';

function ProductCard({ producto }) {
    const { id, nombre, descripcion, precio, categoriaNombre, talla, color, stock, imagenUrl } = producto;
    const { addToCart } = useCart();
    const [adding, setAdding] = useState(false);

    const handleAdd = async () => {
        if (stock > 0) {
            setAdding(true);
            addToCart(producto);
            setTimeout(() => setAdding(false), 500);
        }
    };

    return (
        <Card className="product-card h-100 shadow-sm border-0">
            <div className="image-container">
                <Card.Img
                    variant="top"
                    src={imagenUrl || `https://via.placeholder.com/300x300?text=${nombre}`}
                    alt={nombre}
                    className="product-image"
                />
            </div>

            <Card.Body className="d-flex flex-column">
                <Card.Title className="fw-semibold">{nombre}</Card.Title>
                <Card.Text className="text-muted small mb-2">
                    {descripcion || 'Sin descripción disponible'}
                </Card.Text>

                <div className="mb-2">
                    <Badge bg="light" text="dark" className="me-1">{categoriaNombre}</Badge>
                    <Badge bg="light" text="dark" className="me-1">Talla {talla}</Badge>
                    <Badge bg="light" text="dark">{color}</Badge>
                </div>

                <div className="d-flex justify-content-between align-items-center mt-auto mb-2">
                    <span className="fw-bold">${precio}</span>
                    <Badge bg={stock > 0 ? 'success' : 'danger'}>
                        {stock > 0 ? `Stock: ${stock}` : 'Agotado'}
                    </Badge>
                </div>

                <div className="mt-auto d-grid gap-2">
                    <Link to={`/producto/${id}`} className="text-decoration-none">
                        <Button variant="dark" className="rounded-pill w-100">
                            Ver Detalles
                        </Button>
                    </Link>
                    <Button
                        variant="outline-dark"
                        disabled={stock === 0 || adding}
                        className="rounded-pill w-100"
                        onClick={handleAdd}
                    >
                        {adding ? '✓ Agregado!' : stock > 0 ? 'Agregar al Carrito' : 'Sin Stock'}
                    </Button>
                </div>
            </Card.Body>
        </Card>
    );
}

export default ProductCard;
