package espe.edu.tienda_ropa.controller;

import espe.edu.tienda_ropa.model.Cliente;
import espe.edu.tienda_ropa.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> obtenerClientesActivos() {
        List<Cliente> clientes = clienteService.obtenerClientesActivos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorId(id);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> obtenerClientePorEmail(@PathVariable String email) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorEmail(email);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteService.guardarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id,
                                                   @Valid @RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarCliente(@PathVariable Long id) {
        try {
            clienteService.desactivarCliente(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activarCliente(@PathVariable Long id) {
        try {
            clienteService.activarCliente(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarClientes(@RequestParam String q) {
        List<Cliente> clientes = clienteService.buscarClientes(q);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Cliente>> obtenerClientesPorCiudad(@PathVariable String ciudad) {
        List<Cliente> clientes = clienteService.obtenerClientesPorCiudad(ciudad);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/recientes")
    public ResponseEntity<List<Cliente>> obtenerClientesRecientes() {
        List<Cliente> clientes = clienteService.obtenerClientesRecientes();
        return ResponseEntity.ok(clientes);
    }
}
