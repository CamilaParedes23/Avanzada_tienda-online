import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';

function Footer() {
  return (
    <footer className="footer">
      <Container>
        <Row>
          <Col md={4}>
            <h5>Tienda de Ropa</h5>
            <p>La mejor moda al mejor precio. Encuentra tu estilo único con nosotros.</p>
          </Col>
          <Col md={4}>
            <h5>Enlaces Rápidos</h5>
            <ul className="list-unstyled">
              <li><a href="/" className="text-white-50">Inicio</a></li>
              <li><a href="/productos" className="text-white-50">Productos</a></li>
              <li><a href="#" className="text-white-50">Contacto</a></li>
              <li><a href="#" className="text-white-50">Sobre Nosotros</a></li>
            </ul>
          </Col>
          <Col md={4}>
            <h5>Contacto</h5>
            <p className="text-white-50">
              📧 info@tiendaropa.com<br/>
              📞 +593 123 456 789<br/>
              📍 Quito, Ecuador
            </p>
          </Col>
        </Row>
        <hr className="my-4"/>
        <Row>
          <Col className="text-center">
            <p className="text-white-50">&copy; 2024 Tienda de Ropa. Todos los derechos reservados.</p>
          </Col>
        </Row>
      </Container>
    </footer>
  );
}

export default Footer;
