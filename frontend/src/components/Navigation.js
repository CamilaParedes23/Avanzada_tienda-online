// src/components/Navigation.js
import React from "react";
import { Navbar, Nav, Container } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import { useNavigate } from "react-router-dom";
import { useCart } from "../context/CartContext";
import { useAuth } from "../context/AuthContext";

function Navigation() {
    const { user, logout, isAdmin } = useAuth();
    const { getItemCount } = useCart();
    const navigate = useNavigate();

    const itemCount = getItemCount();

    const handleLogout = () => {
        logout();
        navigate("/");
    };

    return (
        <Navbar bg="light" expand="lg" className="shadow-sm px-3 navbar-light">
            <Container>

                {/* LOGO */}
                {isAdmin() ? (
                    <Navbar.Brand className="fw-bold fs-3" style={{pointerEvents: "none"}}>StyleHub</Navbar.Brand>
                ) : (
                    <LinkContainer to="/">
                        <Navbar.Brand className="fw-bold fs-3">StyleHub</Navbar.Brand>
                    </LinkContainer>
                )}


                <Navbar.Toggle aria-controls="main-navbar" />
                <Navbar.Collapse id="main-navbar">

                    {/* ==== MENU IZQUIERDO Y DERECHO COMBINADOS ==== */}
                    <Nav className="w-100">

                        {!isAdmin() && (
                            <>
                                {/* Inicio */}
                                <LinkContainer to="/">
                                    <Nav.Link>Inicio</Nav.Link>
                                </LinkContainer>

                                {/* Productos (visible para todos) */}
                                <LinkContainer to="/productos">
                                    <Nav.Link>Productos</Nav.Link>
                                </LinkContainer>
                            </>
                        )}

                        {/* ADMIN */}
                        {user && isAdmin() && (
                            <>
                                <LinkContainer to="/admin">
                                    <Nav.Link className="me-3">Panel Admin</Nav.Link>
                                </LinkContainer>

                                <Nav.Link
                                    className="text-danger fw-semibold ms-auto"
                                    onClick={handleLogout}
                                >
                                    Cerrar Sesión
                                </Nav.Link>
                            </>
                        )}

                        {/* CLIENTE */}
                        {user && !isAdmin() && (
                            <>
                                <LinkContainer to="/carrito">
                                    <Nav.Link className="ms-auto">
                                        Carrito {itemCount > 0 && `(${itemCount})`}
                                    </Nav.Link>
                                </LinkContainer>

                                <Nav.Link
                                    className="text-danger fw-semibold"
                                    onClick={handleLogout}
                                >
                                    Cerrar Sesión
                                </Nav.Link>
                            </>
                        )}

                        {/* VISITANTE */}
                        {!user && (
                            <LinkContainer to="/login" className="ms-auto">
                                <Nav.Link className="btn btn-outline-dark rounded-pill px-3">
                                    Iniciar Sesión
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
