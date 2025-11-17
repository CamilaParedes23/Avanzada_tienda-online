import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { pedidoService } from "../services/api";

function ConfirmacionPedido() {
    const { id } = useParams();
    const [pedido, setPedido] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const cargarPedido = async () => {
            try {
                const response = await pedidoService.obtenerPorId(id);
                setPedido(response.data);
            } catch (error) {
                console.error("Error cargando pedido:", error);
            } finally {
                setLoading(false);
            }
        };

        cargarPedido();
    }, [id]);

    if (loading) {
        return <h2 className="text-center mt-5">Cargando información del pedido...</h2>;
    }

    if (!pedido) {
        return <h2 className="text-center mt-5 text-danger">
            No se encontró información del pedido.
        </h2>;
    }

    // Dar formato bonito al ID → ejemplo: #0003
    const pedidoIdFormateado = `#${String(pedido.id).padStart(4, "0")}`;

    // Formato fecha
    const fecha = new Date(pedido.fechaPedido).toLocaleString("es-ES", {
        dateStyle: "full",
        timeStyle: "short",
    });

    return (
        <div className="container mt-5">
            <div className="card shadow-lg p-4">
                <h1 className="text-center mb-4">
                    🎉 ¡Pedido realizado con éxito!
                </h1>

                <p className="text-center fs-5">
                    Gracias por tu compra. A continuación te mostramos los detalles del pedido:
                </p>

                <hr />

                <div className="mt-3 fs-5">
                    <p><strong>📦 Número de pedido:</strong> {pedidoIdFormateado}</p>
                    <p><strong>📅 Fecha:</strong> {fecha}</p>
                    <p><strong>💲 Total:</strong> ${pedido.total}</p>
                    <p><strong>✅ Estado:</strong>
                        <span className={`badge ms-2 ${
                            pedido.estado === 'COMPLETADO' ? 'bg-success' :
                            pedido.estado === 'PENDIENTE' ? 'bg-warning' :
                            pedido.estado === 'CONFIRMADO' ? 'bg-info' :
                            pedido.estado === 'CANCELADO' ? 'bg-danger' : 'bg-secondary'
                        }`}>
                            {pedido.estado === 'COMPLETADO' ? '✅ COMPLETADO' :
                             pedido.estado === 'PENDIENTE' ? '⏳ PENDIENTE' :
                             pedido.estado === 'CONFIRMADO' ? '✔️ CONFIRMADO' :
                             pedido.estado === 'CANCELADO' ? '❌ CANCELADO' :
                             pedido.estado}
                        </span>
                    </p>
                    {pedido.direccionEnvio && (
                        <p><strong>📍 Dirección de envío:</strong> {pedido.direccionEnvio}</p>
                    )}
                </div>

                <div className="text-center mt-4">
                    <Link to="/productos" className="btn btn-primary">
                        Seguir comprando 🛍️
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default ConfirmacionPedido;
