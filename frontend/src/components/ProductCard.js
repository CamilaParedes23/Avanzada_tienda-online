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
        <Card className="product-card h-100 animate-fade-in-up">
            <div className="image-container position-relative overflow-hidden">
                <Card.Img
                    variant="top"
                    src={imagenUrl || `https://via.placeholder.com/350x350?text=${encodeURIComponent(nombre)}`}
                    alt={nombre}
                    className="card-img-top"
                />
                {stock === 0 && (
                    <div className="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center" 
                         style={{ backgroundColor: 'rgba(0,0,0,0.7)' }}>
                        <span className="text-white fw-bold fs-4">AGOTADO</span>
                    </div>
                )}
            </div>

            <Card.Body className="d-flex flex-column p-4">
                <Card.Title className="card-title mb-2">{nombre}</Card.Title>
                
                <div className="mb-3">
                    <Badge bg="secondary" className="me-1">{categoriaNombre}</Badge>
                    <Badge bg="info" className="me-1">Talla {talla}</Badge>
                    <Badge bg="warning" text="dark">{color}</Badge>
                </div>

                <Card.Text className="text-muted small mb-3 flex-grow-1">
                    {descripcion || 'Descubre este increíble producto de nuestra colección exclusiva.'}
                </Card.Text>

                <div className="d-flex justify-content-between align-items-center mb-3">
                    <span className="price-tag">${precio?.toFixed(2)}</span>
                    <Badge 
                        bg={stock > 0 ? 'success' : 'danger'} 
                        className="stock-badge"
                    >
                        {stock > 0 ? `${stock} disponible${stock > 1 ? 's' : ''}` : 'Agotado'}
                    </Badge>
                </div>

                <div className="d-grid gap-2 mt-auto">
                    <Link to={`/producto/${id}`} className="text-decoration-none">
                        <Button variant="outline-primary" className="w-100">
                            Ver Detalles
                        </Button>
                    </Link>
                    <Button
                        variant="primary"
                        disabled={stock === 0 || adding}
                        className="w-100"
                        onClick={handleAdd}
                    >
                        {adding ? '✓ ¡Agregado!' : stock > 0 ? '🛒 Agregar al Carrito' : 'Sin Stock'}
                    </Button>
                </div>
            </Card.Body>
        </Card>
    );
}

export default ProductCard;
