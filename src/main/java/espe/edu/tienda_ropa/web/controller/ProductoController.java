package espe.edu.tienda_ropa.web.controller;

import espe.edu.tienda_ropa.dto.ProductoRequestData;
import espe.edu.tienda_ropa.dto.ProductoResponse;
import espe.edu.tienda_ropa.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<ProductoResponse> create(@Valid @RequestBody ProductoRequestData request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.create(request));
    }

    @GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ProductoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getById(id));
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<ProductoResponse>> getAll() {
        return ResponseEntity.ok(productoService.list());
    }

    @PutMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ProductoResponse> update(@PathVariable Long id,
            @Valid @RequestBody ProductoRequestData request) {
        return ResponseEntity.ok(productoService.update(id, request));
    }

    @PatchMapping(value = "/{id}/deactivate", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ProductoResponse> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.deactivate(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
