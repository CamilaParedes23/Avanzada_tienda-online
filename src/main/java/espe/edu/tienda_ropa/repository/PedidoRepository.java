package espe.edu.tienda_ropa.repository;

import espe.edu.tienda_ropa.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByEstado(Pedido.EstadoPedido estado);

    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin ORDER BY p.fechaPedido DESC")
    List<Pedido> findByFechaPedidoBetween(@Param("fechaInicio") LocalDateTime fechaInicio,
                                          @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = :clienteId ORDER BY p.fechaPedido DESC")
    List<Pedido> findHistorialPedidosByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT p FROM Pedido p ORDER BY p.fechaPedido DESC")
    List<Pedido> findAllOrderByFechaDesc();

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.estado = :estado")
    Long countByEstado(@Param("estado") Pedido.EstadoPedido estado);

    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.estado = 'ENTREGADO' AND " +
           "p.fechaPedido BETWEEN :fechaInicio AND :fechaFin")
    Double calcularVentasPorPeriodo(@Param("fechaInicio") LocalDateTime fechaInicio,
                                   @Param("fechaFin") LocalDateTime fechaFin);
}
