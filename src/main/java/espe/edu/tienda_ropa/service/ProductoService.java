package espe.edu.tienda_ropa.service;

import espe.edu.tienda_ropa.model.Producto;
import espe.edu.tienda_ropa.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        if (productoRepository.existsById(id)) {
            producto.setId(id);
            return productoRepository.save(producto);
        }
        throw new RuntimeException("Producto no encontrado con id: " + id);
    }

    public void eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContaining(nombre);
    }

    public List<Producto> obtenerProductosDisponibles() {
        return productoRepository.findProductosDisponibles();
    }

    public List<String> obtenerCategorias() {
        return productoRepository.findDistinctCategorias();
    }

    public List<String> obtenerTallas() {
        return productoRepository.findDistinctTallas();
    }

    public List<String> obtenerColores() {
        return productoRepository.findDistinctColores();
    }
}
