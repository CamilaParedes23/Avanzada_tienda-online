package espe.edu.tienda_ropa.service;

import espe.edu.tienda_ropa.model.Categoria;
import espe.edu.tienda_ropa.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> obtenerCategoriasActivas() {
        return categoriaRepository.findCategoriasActivas();
    }

    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Optional<Categoria> obtenerCategoriaPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }

    public Categoria guardarCategoria(Categoria categoria) {
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Long id, Categoria categoria) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con id: " + id);
        }

        // Verificar si el nombre ya existe en otra categoría
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNombre(categoria.getNombre());
        if (categoriaExistente.isPresent() && !categoriaExistente.get().getId().equals(id)) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }

        categoria.setId(id);
        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con id: " + id);
        }

        // Verificar si la categoría tiene productos asociados
        Long cantidadProductos = categoriaRepository.countProductosByCategoriaId(id);
        if (cantidadProductos > 0) {
            throw new RuntimeException("No se puede eliminar la categoría porque tiene " +
                                     cantidadProductos + " producto(s) asociado(s)");
        }

        categoriaRepository.deleteById(id);
    }

    public void desactivarCategoria(Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            categoria.setActiva(false);
            categoriaRepository.save(categoria);
        } else {
            throw new RuntimeException("Categoría no encontrada con id: " + id);
        }
    }

    public void activarCategoria(Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            categoria.setActiva(true);
            categoriaRepository.save(categoria);
        } else {
            throw new RuntimeException("Categoría no encontrada con id: " + id);
        }
    }
}
