import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function Footer() {
    return (
        <footer className="footer bg-dark text-light py-4 mt-5">
            <Container>
                <Row>
                    <Col md={4} className="mb-3">
                        <h5>Tienda de Ropa</h5>
                        <p className="text-white-50">
                            La mejor moda al mejor precio. Encuentra tu estilo único con nosotros.
                        </p>
                    </Col>

                    <Col md={4} className="mb-3">
                        <h5>Enlaces Rápidos</h5>
                        <ul className="list-unstyled">
                            <li>
                                <Link to="/" className="text-white-50 text-decoration-none">
                                    Inicio
                                </Link>
                            </li>
                            <li>
                                <Link to="/productos" className="text-white-50 text-decoration-none">
                                    Productos
                                </Link>
                            </li>
                            <li>
                                <Link to="/contacto" className="text-white-50 text-decoration-none">
                                    Contacto
                                </Link>
                            </li>
                            <li>
                                <Link to="/sobre-nosotros" className="text-white-50 text-decoration-none">
                                    Sobre Nosotros
                                </Link>
                            </li>
                        </ul>
                    </Col>

                    <Col md={4}>
                        <h5>Contacto</h5>
                        <p className="text-white-50 mb-1">📧 info@tiendaropa.com</p>
                        <p className="text-white-50 mb-1">📞 +593 123 456 789</p>
                        <p className="text-white-50 mb-0">📍 Quito, Ecuador</p>
                    </Col>
                </Row>

                <hr className="my-4 border-light" />

                <Row>
                    <Col className="text-center">
                        <p className="text-white-50 mb-0">
                            &copy; {new Date().getFullYear()} Tienda de Ropa. Todos los derechos reservados.
                        </p>
                    </Col>
                </Row>
            </Container>
        </footer>
    );
}

export default Footer;
