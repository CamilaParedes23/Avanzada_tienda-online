package espe.edu.tienda_ropa.service;

import espe.edu.tienda_ropa.model.Cliente;
import espe.edu.tienda_ropa.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    public List<Cliente> obtenerClientesActivos() {
        return clienteRepository.findByActivoTrue();
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> obtenerClientePorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Cliente guardarCliente(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con el email: " + cliente.getEmail());
        }
        cliente.setFechaRegistro(LocalDateTime.now());
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }

        // Verificar si el email ya existe en otro cliente
        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteExistente.isPresent() && !clienteExistente.get().getId().equals(id)) {
            throw new RuntimeException("Ya existe un cliente con el email: " + cliente.getEmail());
        }

        cliente.setId(id);
        // Mantener la fecha de registro original
        Optional<Cliente> clienteOriginal = clienteRepository.findById(id);
        if (clienteOriginal.isPresent()) {
            cliente.setFechaRegistro(clienteOriginal.get().getFechaRegistro());
        }

        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }

        // Verificar si el cliente tiene pedidos
        Long cantidadPedidos = clienteRepository.countPedidosByClienteId(id);
        if (cantidadPedidos > 0) {
            throw new RuntimeException("No se puede eliminar el cliente porque tiene " +
                                     cantidadPedidos + " pedido(s) asociado(s)");
        }

        clienteRepository.deleteById(id);
    }

    public void desactivarCliente(Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setActivo(false);
            clienteRepository.save(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
    }

    public void activarCliente(Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setActivo(true);
            clienteRepository.save(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
    }

    public List<Cliente> buscarClientes(String busqueda) {
        return clienteRepository.buscarClientes(busqueda);
    }

    public List<Cliente> obtenerClientesPorCiudad(String ciudad) {
        return clienteRepository.findByCiudad(ciudad);
    }

    public List<Cliente> obtenerClientesRecientes() {
        return clienteRepository.findClientesRecientes();
    }
}
