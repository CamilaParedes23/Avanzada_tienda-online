// src/App.js
import React from "react";
import { BrowserRouter as Router, Routes, Route, useLocation } from "react-router-dom";

import Navigation from "./components/Navigation";
import Footer from "./components/Footer";

import Home from "./pages/Home";
import ProductList from "./pages/ProductList";
import ProductDetail from "./pages/ProductDetail";
import Cart from "./pages/Cart";
import Login from "./pages/Login";
import AdminPanel from "./pages/AdminPanel";
import ConfirmacionPedido from "./pages/ConfirmacionPedido";

import { AuthProvider } from "./context/AuthContext";
import { CartProvider } from "./context/CartContext";

// Nuevo componente para gestionar el layout
const Layout = () => {
    const location = useLocation();
    const isAdminPage = location.pathname === '/admin';

    return (
        <div className="App" style={{ display: "flex", minHeight: "100vh", flexDirection: "column" }}>
            <Navigation />
            <main style={{ flex: 1, padding: "20px 0" }}>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/productos" element={<ProductList />} />
                    <Route path="/producto/:id" element={<ProductDetail />} />
                    <Route path="/carrito" element={<Cart />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/admin" element={<AdminPanel />} />
                    <Route path="/confirmacion-pedido/:id" element={<ConfirmacionPedido />} />
                </Routes>
            </main>
            {!isAdminPage && <Footer />}
        </div>
    );
};

function App() {
    return (
        <AuthProvider>
            <CartProvider>
                <Router>
                    <Layout />
                </Router>
            </CartProvider>
        </AuthProvider>
    );
}

export default App;
