package espe.edu.tienda_ropa.repository;

import espe.edu.tienda_ropa.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByPedidoId(Long pedidoId);

    List<DetallePedido> findByProductoId(Long productoId);

    @Query("SELECT dp FROM DetallePedido dp WHERE dp.pedido.cliente.id = :clienteId")
    List<DetallePedido> findByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT SUM(dp.cantidad) FROM DetallePedido dp WHERE dp.producto.id = :productoId")
    Long calcularCantidadVendidaProducto(@Param("productoId") Long productoId);

    @Query("SELECT dp.producto.id, SUM(dp.cantidad) FROM DetallePedido dp " +
           "GROUP BY dp.producto.id ORDER BY SUM(dp.cantidad) DESC")
    List<Object[]> findProductosMasVendidos();
}
