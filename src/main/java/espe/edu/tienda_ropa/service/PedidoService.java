package espe.edu.tienda_ropa.service;

import espe.edu.tienda_ropa.model.*;
import espe.edu.tienda_ropa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAllOrderByFechaDesc();
    }

    public Optional<Pedido> obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> obtenerPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findHistorialPedidosByClienteId(clienteId);
    }

    public List<Pedido> obtenerPedidosPorEstado(Pedido.EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public List<Pedido> obtenerPedidosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pedidoRepository.findByFechaPedidoBetween(fechaInicio, fechaFin);
    }

    @Transactional
    public Pedido crearPedido(Long clienteId, List<DetallePedidoDTO> detallesDTO) {
        // Validar que existe el cliente
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new RuntimeException("Cliente no encontrado con id: " + clienteId);
        }

        Cliente cliente = clienteOpt.get();

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);

        BigDecimal total = BigDecimal.ZERO;

        // Procesar cada detalle
        for (DetallePedidoDTO detalleDTO : detallesDTO) {
            Optional<Producto> productoOpt = productoRepository.findById(detalleDTO.getProductoId());
            if (!productoOpt.isPresent()) {
                throw new RuntimeException("Producto no encontrado con id: " + detalleDTO.getProductoId());
            }

            Producto producto = productoOpt.get();

            // Validar stock
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre() +
                                         ". Stock disponible: " + producto.getStock());
            }

            // Actualizar stock
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);

            // Calcular subtotal
            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));
            total = total.add(subtotal);
        }

        pedido.setTotal(total);

        // Guardar el pedido primero
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Crear los detalles
        for (DetallePedidoDTO detalleDTO : detallesDTO) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId()).get();

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedidoGuardado);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(detalleDTO.getCantidad())));

            detallePedidoRepository.save(detalle);
        }

        return pedidoGuardado;
    }

    public Pedido actualizarEstadoPedido(Long id, Pedido.EstadoPedido nuevoEstado) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        if (!pedidoOpt.isPresent()) {
            throw new RuntimeException("Pedido no encontrado con id: " + id);
        }

        Pedido pedido = pedidoOpt.get();
        pedido.setEstado(nuevoEstado);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        if (!pedidoOpt.isPresent()) {
            throw new RuntimeException("Pedido no encontrado con id: " + id);
        }

        Pedido pedido = pedidoOpt.get();

        // Solo se puede cancelar si está en estado PENDIENTE o CONFIRMADO
        if (pedido.getEstado() != Pedido.EstadoPedido.PENDIENTE &&
            pedido.getEstado() != Pedido.EstadoPedido.CONFIRMADO) {
            throw new RuntimeException("No se puede cancelar un pedido en estado: " + pedido.getEstado());
        }

        // Restaurar stock de los productos
        List<DetallePedido> detalles = detallePedidoRepository.findByPedidoId(id);
        for (DetallePedido detalle : detalles) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }

        // Cambiar estado a cancelado
        pedido.setEstado(Pedido.EstadoPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    public Long contarPedidosPorEstado(Pedido.EstadoPedido estado) {
        return pedidoRepository.countByEstado(estado);
    }

    public Double calcularVentasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Double ventas = pedidoRepository.calcularVentasPorPeriodo(fechaInicio, fechaFin);
        return ventas != null ? ventas : 0.0;
    }

    // DTO para crear detalles de pedido
    public static class DetallePedidoDTO {
        private Long productoId;
        private Integer cantidad;

        public DetallePedidoDTO() {}

        public DetallePedidoDTO(Long productoId, Integer cantidad) {
            this.productoId = productoId;
            this.cantidad = cantidad;
        }

        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }
    }
}
