package espe.edu.tienda_ropa.web.controller;

import espe.edu.tienda_ropa.domain.Categoria;
import espe.edu.tienda_ropa.domain.Producto;
import espe.edu.tienda_ropa.repository.CategoriaDomainRepository;
import espe.edu.tienda_ropa.repository.ProductoDomainRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final CategoriaDomainRepository categoriaRepo;
    private final ProductoDomainRepository productoRepo;

    public TestController(CategoriaDomainRepository categoriaRepo,
                         ProductoDomainRepository productoRepo) {
        this.categoriaRepo = categoriaRepo;
        this.productoRepo = productoRepo;
    }

    @GetMapping
    public String test() {
        return "API funcionando correctamente";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/create-sample-data")
    public Map<String, Object> createSampleData() {
        Map<String, Object> result = new HashMap<>();

        try {
            // Limpiar datos existentes
            productoRepo.deleteAll();
            categoriaRepo.deleteAll();

            // Crear categorías
            Categoria camisas = new Categoria();
            camisas.setNombre("Camisas");
            camisas.setDescripcion("Camisas casuales y formales");
            camisas = categoriaRepo.save(camisas);

            Categoria pantalones = new Categoria();
            pantalones.setNombre("Pantalones");
            pantalones.setDescripcion("Pantalones de vestir y casuales");
            pantalones = categoriaRepo.save(pantalones);

            Categoria chaquetas = new Categoria();
            chaquetas.setNombre("Chaquetas");
            chaquetas.setDescripcion("Chaquetas y abrigos");
            chaquetas = categoriaRepo.save(chaquetas);

            // Crear productos
            Producto producto1 = new Producto();
            producto1.setNombre("Camisa Blanca Clásica");
            producto1.setDescripcion("Camisa blanca de algodón para oficina");
            producto1.setPrecio(new BigDecimal("45.99"));
            producto1.setStock(25);
            producto1.setCategoria(camisas);
            producto1.setTalla("M");
            producto1.setColor("Blanco");
            productoRepo.save(producto1);

            Producto producto2 = new Producto();
            producto2.setNombre("Pantalón Negro Formal");
            producto2.setDescripcion("Pantalón de vestir negro");
            producto2.setPrecio(new BigDecimal("89.99"));
            producto2.setStock(15);
            producto2.setCategoria(pantalones);
            producto2.setTalla("L");
            producto2.setColor("Negro");
            productoRepo.save(producto2);

            Producto producto3 = new Producto();
            producto3.setNombre("Chaqueta de Cuero");
            producto3.setDescripcion("Chaqueta de cuero genuino");
            producto3.setPrecio(new BigDecimal("199.99"));
            producto3.setStock(8);
            producto3.setCategoria(chaquetas);
            producto3.setTalla("XL");
            producto3.setColor("Marrón");
            productoRepo.save(producto3);

            result.put("success", true);
            result.put("message", "Datos de prueba creados exitosamente");
            result.put("totalCategorias", categoriaRepo.count());
            result.put("totalProductos", productoRepo.count());

        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
