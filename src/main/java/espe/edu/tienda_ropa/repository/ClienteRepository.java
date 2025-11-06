package espe.edu.tienda_ropa.repository;

import espe.edu.tienda_ropa.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByActivoTrue();

    @Query("SELECT c FROM Cliente c WHERE c.activo = true AND " +
           "(LOWER(c.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(c.apellido) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :busqueda, '%')))")
    List<Cliente> buscarClientes(@Param("busqueda") String busqueda);

    boolean existsByEmail(String email);

    List<Cliente> findByCiudad(String ciudad);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.cliente.id = :clienteId")
    Long countPedidosByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT c FROM Cliente c WHERE c.activo = true ORDER BY c.fechaRegistro DESC")
    List<Cliente> findClientesRecientes();
}
