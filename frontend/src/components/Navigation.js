import React, { useState, useEffect } from "react";
import { Navbar, Nav, Container, Badge, NavDropdown } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import { useNavigate } from "react-router-dom";
import { useCart } from "../context/CartContext";
import { useAuth } from "../context/AuthContext";
import { categoriaService } from "../services/api";

function Navigation() {
  const { getItemCount } = useCart();
  const { user, logout, isAdmin, isCliente } = useAuth();
  const itemCount = getItemCount();
  const navigate = useNavigate();
  const [categorias, setCategorias] = useState([]);

  useEffect(() => {
    const cargarCategorias = async () => {
      try {
        const response = await categoriaService.obtenerTodos();
        const categoriasData = response?.data || [];
        // Eliminar duplicados por nombre
        const categoriasUnicas = Array.from(
          new Map(categoriasData.map((cat) => [cat.nombre, cat])).values()
        );
        setCategorias(categoriasUnicas.slice(0, 6)); // Mostrar máximo 6 categorías
      } catch (error) {
        console.error("Error al cargar categorías:", error);
      }
    };

    cargarCategorias();
  }, []);

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  const handleCategoriaClick = (nombreCategoria) => {
    navigate(`/productos?categoria=${encodeURIComponent(nombreCategoria)}`);
  };

  return (
    <Navbar bg="light" variant="light" expand="lg" className="navbar">
      <Container>
        <LinkContainer to="/">
          <Navbar.Brand>🛍️ StyleHub</Navbar.Brand>
        </LinkContainer>

        {/* <Navbar.Toggle aria-controls="basic-navbar-nav" /> */}
        <Navbar.Collapse id="basic-navbar-nav" className="items-menu-container">
          <Nav className="me-auto">
            <LinkContainer to="/">
              <Nav.Link>Inicio</Nav.Link>
            </LinkContainer>
            <LinkContainer to="/productos">
              <Nav.Link>Productos</Nav.Link>
            </LinkContainer>

            {/* Mostrar Admin solo si es administrador */}
            {user && isAdmin() && (
              <LinkContainer to="/admin">
                <Nav.Link>Admin Panel</Nav.Link>
              </LinkContainer>
            )}
          </Nav>

          <Nav className="ms-auto align-items-center">
            {/* Carrito solo para clientes autenticados */}
            {user && isCliente() && (
              <LinkContainer to="/carrito">
                <Nav.Link className="position-relative me-3">
                  🛒 Carrito
                  {itemCount > 0 && (
                    <Badge
                      bg="danger"
                      className="position-absolute top-0 start-100 translate-middle"
                      style={{ fontSize: "0.7em" }}
                    >
                      {itemCount}
                    </Badge>
                  )}
                </Nav.Link>
              </LinkContainer>
            )}

            {/* Mostrar login o usuario */}
            {user ? (
              <NavDropdown
                title={`👤 ${user.nombre}`}
                id="user-nav-dropdown"
                align="end"
              >
                <NavDropdown.Item disabled>
                  <small className="text-muted">
                    {isAdmin() ? "🔧 Administrador" : "👥 Cliente"}
                  </small>
                </NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>
                  🚪 Cerrar Sesión
                </NavDropdown.Item>
              </NavDropdown>
            ) : (
              <LinkContainer to="/login">
                <Nav.Link className="btn btn-outline-light btn-sm">
                  🔐 Iniciar Sesión
                </Nav.Link>
              </LinkContainer>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Navigation;
