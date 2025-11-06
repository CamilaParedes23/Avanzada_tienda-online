package espe.edu.tienda_ropa.repository;

import espe.edu.tienda_ropa.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNombre(String nombre);

    List<Categoria> findByActivaTrue();

    @Query("SELECT c FROM Categoria c WHERE c.activa = true ORDER BY c.nombre")
    List<Categoria> findCategoriasActivas();

    boolean existsByNombre(String nombre);

    @Query("SELECT COUNT(p) FROM Producto p WHERE p.categoriaObj.id = :categoriaId")
    Long countProductosByCategoriaId(Long categoriaId);
}
