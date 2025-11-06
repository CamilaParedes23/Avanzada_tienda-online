package espe.edu.tienda_ropa.repository;

import espe.edu.tienda_ropa.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByTalla(String talla);

    List<Producto> findByColor(String color);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre%")
    List<Producto> findByNombreContaining(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p WHERE p.stock > 0")
    List<Producto> findProductosDisponibles();

    @Query("SELECT DISTINCT p.categoria FROM Producto p")
    List<String> findDistinctCategorias();

    @Query("SELECT DISTINCT p.talla FROM Producto p")
    List<String> findDistinctTallas();

    @Query("SELECT DISTINCT p.color FROM Producto p")
    List<String> findDistinctColores();
}
