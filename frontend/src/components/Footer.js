import React, { useState, useEffect } from "react";
import { Container, Row, Col } from "react-bootstrap";

function Footer() {
    const [visitCount, setVisitCount] = useState(0);

    useEffect(() => {
        // Simular contador de visitas
        const storedCount = localStorage.getItem('visitCount');
        const currentCount = storedCount ? parseInt(storedCount) + 1 : Math.floor(Math.random() * 1000) + 100;
        setVisitCount(currentCount);
        localStorage.setItem('visitCount', currentCount.toString());
    }, []);

    return (
        <footer className="footer mt-5">
            <Container>

                <Row className="mb-4">

                    {/* CONTACTO */}
                    <Col lg={4} className="mb-4">
                        <center><h5>Contacto</h5></center>

                        <div className="contact-grid">
                            <div className="item">
                                <span className="label">Correo:</span>
                                <a href="mailto:info@tiendaropa.com">info@tiendaropa.com</a>
                            </div>

                            <div className="item center">
                                <div className="contact-icon">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                        <path d="m22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"></path>
                                    </svg>
                                </div>
                                <span className="label">Tel:</span>
                                <span>099 162 3861</span>
                            </div>

                            <div className="item right">
                                <div className="contact-icon">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                        <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
                                        <circle cx="12" cy="10" r="3"></circle>
                                    </svg>
                                </div>
                                <span>Quito, Ecuador</span>
                            </div>
                        </div>
                    </Col>

                    {/* CONTADOR DE VISITAS */}
                    <Col lg={4} className="mb-4">
                        <center><h5>Estadísticas</h5></center>
                        <div className="visit-counter">
                            <div className="counter-icon">
                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                    <path d="M1 12s4-8 11-8 11 8-11 8 11-8 11-8-11-8-11 8z"></path>
                                    <circle cx="12" cy="12" r="3"></circle>
                                </svg>
                            </div>
                            <div className="counter-content">
                                <span className="counter-label">Visitante #</span>
                                <span className="counter-number">{visitCount.toLocaleString()}</span>
                            </div>
                        </div>
                    </Col>

                </Row>

                <div className="copyright">
                    © {new Date().getFullYear()} Tienda de Ropa — Todos los derechos reservados.
                    <div className="mt-2">
                        <em className="text-muted small">Tu estilo, tu pasión</em>
                    </div>
                </div>

            </Container>
        </footer>
    );
}

export default Footer;
