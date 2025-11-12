import React, { useState, useEffect, useCallback } from "react";
import { Container, Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import { productoService, categoriaService } from "../services/api";
import ProductCard from "../components/ProductCard";
import "../App.css";

function Home() {
  const [productosDestacados, setProductosDestacados] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(true);

  const cargarDatos = useCallback(async () => {
    try {
      setLoading(true);
      const [productosResponse, categoriasResponse] = await Promise.all([
        productoService.obtenerTodos(),
        categoriaService.obtenerTodos(),
      ]);

      const productos = productosResponse?.data || [];
      const categoriasList = categoriasResponse?.data || [];

      // ✅ Filtrar solo productos con stock > 0 y mostrar los primeros 6
      const productosDisponibles = productos.filter((p) => p.stock > 0);
      setProductosDestacados(productosDisponibles.slice(0, 6));

      // ✅ Eliminar categorías duplicadas por nombre
      const categoriasUnicas = Array.from(
        new Map(categoriasList.map((cat) => [cat.nombre, cat])).values()
      );

      setCategorias(categoriasUnicas);
    } catch (error) {
      console.error("Error al cargar datos:", error);
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
      {/* HERO */}
      <section className="hero-section text-center mb-5 p-5 animate-fade-in-up">
        <h1 className="display-4 fw-bold mb-4">Bienvenido a StyleHub</h1>
        <p className="lead text-muted mb-4 fs-5">
          Descubre las últimas tendencias en moda premium. Calidad excepcional,
          estilo único y los mejores precios en un solo lugar.
        </p>
        <Link to="/productos">
          <Button variant="primary" size="lg" className="me-3">
            🛍️ Explorar Productos
          </Button>
        </Link>
        <Link to="/productos">
          <Button variant="outline-primary" size="lg">
            Ver Colección
          </Button>
        </Link>
      </section>

      {/* CATEGORÍAS */}
      <section className="categories-section text-center mb-5">
        <h2 className="fw-bold mb-5">Explora Nuestras Categorías</h2>
        {categorias.length > 0 ? (
          <div className="categories-grid">
            {categorias.map((categoria) => (
              <div key={categoria.id} className="category-card">
                <h5 className="mb-3">{categoria.nombre}</h5>
                <p className="text-muted small mb-3">
                  Descubre nuestra colección de {categoria.nombre.toLowerCase()}
                </p>
                <Link to={`/productos?categoria=${categoria.nombre}`}>
                  <Button variant="outline-primary">
                    Explorar {categoria.nombre}
                  </Button>
                </Link>
              </div>
            ))}
          </div>
        ) : (
          <div className="text-center py-5">
            <p className="text-muted fs-5">
              No hay categorías disponibles en este momento.
            </p>
            <div className="mt-3" style={{ fontSize: "4rem", opacity: 0.3 }}>
              📦
            </div>
          </div>
        )}
      </section>

      {/* PRODUCTOS DESTACADOS */}
      <section className="featured-section">
        <div className="text-center mb-5">
          <h2 className="fw-bold mb-3">Productos Destacados</h2>
          <p className="text-muted fs-6">
            Selección exclusiva de nuestros productos más populares
          </p>
        </div>

        {loading ? (
          <div className="text-center py-5">
            <div
              className="spinner-border text-primary"
              role="status"
              style={{ width: "3rem", height: "3rem" }}
            >
              <span className="visually-hidden">Cargando productos...</span>
            </div>
            <p className="text-muted mt-3">Cargando productos destacados...</p>
          </div>
        ) : productosDestacados.length > 0 ? (
          <div className="products-grid">
            {productosDestacados.map((producto, index) => (
              <div
                key={producto.id}
                className="product-cell"
                style={{ animationDelay: `${index * 0.1}s` }}
              >
                <ProductCard producto={producto} />
              </div>
            ))}
          </div>
        ) : (
          <div className="text-center py-5">
            <div style={{ fontSize: "5rem", opacity: 0.3 }}>🛒</div>
            <h4 className="text-muted mt-3">No hay productos disponibles</h4>
            <p className="text-muted">
              ¡Pronto tendremos nuevos productos para ti!
            </p>
            <Link to="/admin">
              <Button variant="primary" className="mt-3">
                Administrar Productos
              </Button>
            </Link>
          </div>
        )}
      </section>
    </Container>
  );
}

export default Home;
