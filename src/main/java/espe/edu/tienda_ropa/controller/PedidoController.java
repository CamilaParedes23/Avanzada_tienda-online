package espe.edu.tienda_ropa.controller;

import espe.edu.tienda_ropa.model.Pedido;
import espe.edu.tienda_ropa.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.obtenerPedidoPorId(id);
        return pedido.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorEstado(@PathVariable Pedido.EstadoPedido estado) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorEstado(estado);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorFecha(fechaInicio, fechaFin);
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping("/crear")
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody CrearPedidoRequest request) {
        try {
            Pedido nuevoPedido = pedidoService.crearPedido(request.getClienteId(), request.getDetalles());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstadoPedido(@PathVariable Long id,
                                                        @RequestBody ActualizarEstadoRequest request) {
        try {
            Pedido pedidoActualizado = pedidoService.actualizarEstadoPedido(id, request.getEstado());
            return ResponseEntity.ok(pedidoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        try {
            pedidoService.cancelarPedido(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/estadisticas/estado/{estado}")
    public ResponseEntity<Long> contarPedidosPorEstado(@PathVariable Pedido.EstadoPedido estado) {
        Long cantidad = pedidoService.contarPedidosPorEstado(estado);
        return ResponseEntity.ok(cantidad);
    }

    @GetMapping("/estadisticas/ventas")
    public ResponseEntity<Double> calcularVentasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        Double ventas = pedidoService.calcularVentasPorPeriodo(fechaInicio, fechaFin);
        return ResponseEntity.ok(ventas);
    }

    // DTOs para las requests
    public static class CrearPedidoRequest {
        private Long clienteId;
        private List<PedidoService.DetallePedidoDTO> detalles;

        public Long getClienteId() {
            return clienteId;
        }

        public void setClienteId(Long clienteId) {
            this.clienteId = clienteId;
        }

        public List<PedidoService.DetallePedidoDTO> getDetalles() {
            return detalles;
        }

        public void setDetalles(List<PedidoService.DetallePedidoDTO> detalles) {
            this.detalles = detalles;
        }
    }

    public static class ActualizarEstadoRequest {
        private Pedido.EstadoPedido estado;

        public Pedido.EstadoPedido getEstado() {
            return estado;
        }

        public void setEstado(Pedido.EstadoPedido estado) {
            this.estado = estado;
        }
    }
}
