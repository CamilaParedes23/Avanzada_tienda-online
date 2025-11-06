package espe.edu.tienda_ropa.controller;

import espe.edu.tienda_ropa.model.Producto;
import espe.edu.tienda_ropa.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    public void testObtenerTodosLosProductos() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Camiseta Test");
        producto.setPrecio(new BigDecimal("25.99"));
        producto.setCategoria("Camisetas");
        producto.setTalla("M");
        producto.setColor("Azul");
        producto.setStock(10);

        when(productoService.obtenerTodosLosProductos()).thenReturn(Arrays.asList(producto));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value("Camiseta Test"))
                .andExpect(jsonPath("$[0].precio").value(25.99));
    }

    @Test
    public void testObtenerProductoPorId() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Camiseta Test");
        producto.setPrecio(new BigDecimal("25.99"));

        when(productoService.obtenerProductoPorId(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Camiseta Test"));
    }

    @Test
    public void testProductoNoEncontrado() throws Exception {
        when(productoService.obtenerProductoPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/productos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCrearProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Nuevo Producto");
        producto.setPrecio(new BigDecimal("30.00"));
        producto.setCategoria("Camisetas");
        producto.setTalla("L");
        producto.setColor("Rojo");
        producto.setStock(5);

        when(productoService.guardarProducto(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Nuevo Producto\",\"precio\":30.00,\"categoria\":\"Camisetas\",\"talla\":\"L\",\"color\":\"Rojo\",\"stock\":5}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Nuevo Producto"));
    }
}
