package espe.edu.tienda_ropa.service;

import espe.edu.tienda_ropa.domain.Categoria;
import espe.edu.tienda_ropa.domain.Producto;
import espe.edu.tienda_ropa.dto.ProductoRequestData;
import espe.edu.tienda_ropa.dto.ProductoResponse;
import espe.edu.tienda_ropa.repository.CategoriaDomainRepository;
import espe.edu.tienda_ropa.repository.ProductoDomainRepository;
import espe.edu.tienda_ropa.service.impl.ProductoServiceImpl;
import espe.edu.tienda_ropa.web.advice.ConflictException;
import espe.edu.tienda_ropa.web.advice.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Tests unitarios para ProductoServiceImpl
 *
 * Este conjunto de pruebas valida:
 * - reglas de negocio del dominio Producto
 * - validaciones semánticas
 *
 * Beneficio:
 * Garantiza que la lógica de negocio se mantenga estable
 * independientemente del controlador, API REST o base de datos.
 */
class ProductoServiceImplTest {
    private ProductoDomainRepository repo;
    private CategoriaDomainRepository categoriaRepo;
    private ProductoServiceImpl service;
    /**
     * Inicialización de mocks antes de cada test.
     * Garantiza que solo se prueba lógica de negocio.
     */
    @BeforeEach
    void setUp() {
        repo = mock(ProductoDomainRepository.class);
        categoriaRepo = mock(CategoriaDomainRepository.class);
        service = new ProductoServiceImpl(repo, categoriaRepo);
    }
    // CREATE
    /**
     * PRUEBA: Creación válida de producto
     * Qué valida:
     * - relación válida con categoría
     * - persistencia correcta
     * - conversión Entity → DTO
     * Beneficio:
     * - garantiza funcionamiento del flujo principal del sistema
     */
    @Test
    void createProducto_validData_shouldSaveAndReturnResponse() {
        // ---------- ARRANGE ----------
        ProductoRequestData req = new ProductoRequestData();
        req.setNombre("Polo Azul");
        req.setDescripcion("Camisa ligera");
        req.setPrecio(new BigDecimal("20.50"));
        req.setCategoriaId(1L);
        req.setTalla("M");
        req.setColor("Azul");
        req.setStock(10);
        req.setImagenUrl("img.jpg");
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Ropa");
        Producto saved = new Producto();
        saved.setId(1L);
        saved.setNombre(req.getNombre());
        saved.setDescripcion(req.getDescripcion());
        saved.setPrecio(req.getPrecio());
        saved.setCategoria(categoria);
        saved.setTalla(req.getTalla());
        saved.setColor(req.getColor());
        saved.setStock(req.getStock());
        saved.setImagenUrl(req.getImagenUrl());
        when(repo.existsByNombre("Polo Azul")).thenReturn(false);
        when(categoriaRepo.findById(1L)).thenReturn(Optional.of(categoria));
        when(repo.save(any(Producto.class))).thenReturn(saved);
        // ---------- ACT ----------
        ProductoResponse response = service.create(req);
        // ---------- ASSERT ----------
        assertNotNull(response);
        assertEquals("Polo Azul", response.getNombre());
        assertEquals("Ropa", response.getCategoriaNombre());
        assertEquals(10, response.getStock());
        // ---------- VERIFY ----------
        verify(repo, times(1)).existsByNombre("Polo Azul");
        verify(categoriaRepo, times(1)).findById(1L);
        verify(repo, times(1)).save(any(Producto.class));
    }
    /**
     * PRUEBA: Nombre duplicado
     * Qué valida:
     * - regla de unicidad del dominio
     * Beneficio:
     * - evita ambigüedad semántica
     * - evita corrupción de catálogo
     */
    @Test
    void createProducto_duplicateName_shouldThrowConflict() {
        // ---------- ARRANGE ----------
        ProductoRequestData req = new ProductoRequestData();
        req.setNombre("Polo Azul");
        when(repo.existsByNombre("Polo Azul")).thenReturn(true);
        // ---------- ACT + ASSERT ----------
        ConflictException ex = assertThrows(
                ConflictException.class,
                () -> service.create(req)
        );
        assertEquals("El nombre del producto ya esta registrado", ex.getMessage());
        verify(repo, times(1)).existsByNombre("Polo Azul");
        verify(repo, never()).save(any());
    }
    /**
     * PRUEBA: Categoría inexistente
     * Qué valida:
     * - integridad referencial
     * Beneficio:
     * - evita productos huérfanos
     * - protege modelo relacional
     */
    @Test
    void createProducto_categoriaNotFound_shouldThrowNotFound() {
        // ---------- ARRANGE ----------
        ProductoRequestData req = new ProductoRequestData();
        req.setNombre("Polo Azul");
        req.setCategoriaId(1L);
        when(repo.existsByNombre("Polo Azul")).thenReturn(false);
        when(categoriaRepo.findById(1L)).thenReturn(Optional.empty());
        // ---------- ACT + ASSERT ----------
        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> service.create(req)
        );
        assertEquals("Categoria no encontrada", ex.getMessage());
    }
    // GET BY ID
    /**
     * PRUEBA: Búsqueda válida por ID
     */
    @Test
    void getById_validId_shouldReturnProducto() {
        // ---------- ARRANGE ----------
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Ropa");
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Polo");
        producto.setCategoria(categoria);
        when(repo.findById(1L)).thenReturn(Optional.of(producto));
        // ---------- ACT ----------
        ProductoResponse response = service.getById(1L);
        // ---------- ASSERT ----------
        assertNotNull(response);
        assertEquals("Polo", response.getNombre());
        assertEquals("Ropa", response.getCategoriaNombre());
    }
    /**
     * PRUEBA: Producto inexistente
     */
    @Test
    void getById_notFound_shouldThrowNotFound() {
        when(repo.findById(10L)).thenReturn(Optional.empty());
        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> service.getById(10L)
        );
        assertEquals("Producto no encontrado", ex.getMessage());
    }
    // LISTA
    /**
     * PRUEBA: Listado general
     */
    @Test
    void listProductos_shouldReturnList() {

        // ---------- ARRANGE ----------
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Ropa");
        Producto p1 = new Producto();
        p1.setId(1L);
        p1.setNombre("Polo");
        p1.setCategoria(categoria);
        when(repo.findAll()).thenReturn(List.of(p1));

        // ---------- ACT ----------
        List<ProductoResponse> list = service.list();

        // ---------- ASSERT ----------
        assertEquals(1, list.size());
        assertEquals("Polo", list.get(0).getNombre());
    }

    // UPDATE
    /**
     * PRUEBA: Actualización válida
     */
    @Test
    void updateProducto_validData_shouldUpdateCorrectly() {

        // ---------- ARRANGE ----------
        Long id = 1L;
        ProductoRequestData req = new ProductoRequestData();
        req.setNombre("Nuevo Polo");
        req.setDescripcion("Nueva desc");
        req.setPrecio(new BigDecimal("30"));
        req.setCategoriaId(1L);
        req.setTalla("L");
        req.setColor("Negro");
        req.setStock(5);
        req.setImagenUrl("new.jpg");

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Ropa");

        Producto existing = new Producto();
        existing.setId(id);
        existing.setNombre("Polo Viejo");
        existing.setCategoria(categoria);

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.existsByNombre("Nuevo Polo")).thenReturn(false);
        when(categoriaRepo.findById(1L)).thenReturn(Optional.of(categoria));
        when(repo.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);

        // ---------- ACT ----------
        ProductoResponse response = service.update(id, req);

        // ---------- ASSERT ----------
        verify(repo).save(captor.capture());
        Producto saved = captor.getValue();

        assertEquals("Nuevo Polo", saved.getNombre());
        assertEquals(5, saved.getStock());
        assertEquals("Ropa", saved.getCategoria().getNombre());

        assertNotNull(response);
        assertEquals("Nuevo Polo", response.getNombre());
    }
    // DELETE
    /**
     * PRUEBA: Eliminación definitiva
     */
    @Test
    void deleteProducto_validId_shouldDelete() {
        when(repo.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repo, times(1)).deleteById(1L);
    }
    /**
     * PRUEBA: Eliminación de producto inexistente
     */
    @Test
    void deleteProducto_notFound_shouldThrowNotFound() {
        when(repo.existsById(1L)).thenReturn(false);

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> service.delete(1L)
        );

        assertEquals("Producto no encontrado", ex.getMessage());
    }
}