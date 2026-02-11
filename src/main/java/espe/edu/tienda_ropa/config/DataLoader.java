package espe.edu.tienda_ropa.config;

import espe.edu.tienda_ropa.domain.*;
import espe.edu.tienda_ropa.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Collections;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder passwordEncoder;
    private final CategoriaDomainRepository categoriaRepo;
    private final ProductoDomainRepository productoRepo;

    public DataLoader(UsuarioRepository usuarioRepo,
            RolRepository rolRepo,
            PasswordEncoder passwordEncoder,
            CategoriaDomainRepository categoriaRepo,
            ProductoDomainRepository productoRepo) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
        this.categoriaRepo = categoriaRepo;
        this.productoRepo = productoRepo;
    }

    @Override
    public void run(String... args) {
        System.out.println("🚀 DataLoader iniciando...");

        // ============================
        // Crear roles si no existen
        // ============================
        System.out.println("📋 Creando roles...");
        Rol rolCliente = rolRepo.findByNombre("ROLE_USER")
                .orElseGet(() -> rolRepo.save(new Rol("ROLE_USER")));

        Rol rolAdmin = rolRepo.findByNombre("ROLE_ADMIN")
                .orElseGet(() -> rolRepo.save(new Rol("ROLE_ADMIN")));

        // ============================
        // Usuario CLIENTE
        // ============================
        if (!usuarioRepo.existsByEmail("cliente@tiendaropa.com")) {

            Usuario cliente = new Usuario();
            cliente.setNombre("Cliente Ejemplo");
            cliente.setEmail("cliente@tiendaropa.com");
            cliente.setPassword(passwordEncoder.encode("cliente123"));
            cliente.setRoles(Collections.singleton(rolCliente));

            usuarioRepo.save(cliente);
            System.out.println("✔ Cliente creado");
        }

        // ============================
        // Usuario ADMIN
        // ============================
        if (!usuarioRepo.existsByEmail("admin@tiendaropa.com")) {

            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setEmail("admin@tiendaropa.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Collections.singleton(rolAdmin));

            usuarioRepo.save(admin);
            System.out.println("✔ Admin creado");
        }

        // ============================
        // Crear categorías si no existen
        // ============================
        System.out.println("📁 Creando categorías...");
        Categoria camisas = categoriaRepo.findByNombre("Camisas")
                .orElseGet(() -> {
                    Categoria categoria = new Categoria();
                    categoria.setNombre("Camisas");
                    categoria.setDescripcion("Camisas casuales y formales");
                    return categoriaRepo.save(categoria);
                });

        Categoria pantalones = categoriaRepo.findByNombre("Pantalones")
                .orElseGet(() -> {
                    Categoria categoria = new Categoria();
                    categoria.setNombre("Pantalones");
                    categoria.setDescripcion("Pantalones de vestir y casuales");
                    return categoriaRepo.save(categoria);
                });

        Categoria chaquetas = categoriaRepo.findByNombre("Chaquetas")
                .orElseGet(() -> {
                    Categoria categoria = new Categoria();
                    categoria.setNombre("Chaquetas");
                    categoria.setDescripcion("Chaquetas y abrigos");
                    return categoriaRepo.save(categoria);
                });

        // ============================
        // Crear productos si no existen
        // ============================
        System.out.println("🛍️ Verificando productos existentes: " + productoRepo.count());
        if (productoRepo.count() == 0) {
            // Camisa Blanca
            Producto camisa1 = new Producto();
            camisa1.setNombre("Camisa Blanca Clásica");
            camisa1.setDescripcion("Camisa blanca de algodón para oficina");
            camisa1.setPrecio(new BigDecimal("45.99"));
            camisa1.setStock(25);
            camisa1.setCategoria(camisas);
            camisa1.setTalla("M");
            camisa1.setColor("Blanco");
            camisa1.setImagenUrl(
                    "https://png.pngtree.com/png-vector/20240628/ourmid/pngtree-white-shirt-isolated-on-transparent-background-png-image_12893964.png");
            camisa1.setActivo(true);
            productoRepo.save(camisa1);

            // Pantalón Negro
            Producto pantalon1 = new Producto();
            pantalon1.setNombre("Pantalón Negro Formal");
            pantalon1.setDescripcion("Pantalón de vestir negro");
            pantalon1.setPrecio(new BigDecimal("89.99"));
            pantalon1.setStock(15);
            pantalon1.setCategoria(pantalones);
            pantalon1.setTalla("L");
            pantalon1.setColor("Negro");
            pantalon1.setImagenUrl(
                    "https://png.pngtree.com/png-clipart/20220711/ourmid/pngtree-black-jeans-pants-png-image_5941418.png");
            pantalon1.setActivo(true);
            productoRepo.save(pantalon1);

            // Chaqueta de Cuero
            Producto chaqueta1 = new Producto();
            chaqueta1.setNombre("Chaqueta de Cuero");
            chaqueta1.setDescripcion("Chaqueta de cuero genuino");
            chaqueta1.setPrecio(new BigDecimal("199.99"));
            chaqueta1.setStock(8);
            chaqueta1.setCategoria(chaquetas);
            chaqueta1.setTalla("XL");
            chaqueta1.setColor("Marrón");
            chaqueta1.setImagenUrl("https://w7.pngwing.com/pngs/721/19/png-transparent-leather-jacket.png");
            chaqueta1.setActivo(true);
            productoRepo.save(chaqueta1);

            System.out.println("✔ Productos de prueba creados");
        }

        System.out.println("🎉 DataLoader completado exitosamente!");
        System.out.println("📊 Total productos: " + productoRepo.count());
        System.out.println("📊 Total categorías: " + categoriaRepo.count());
    }
}
