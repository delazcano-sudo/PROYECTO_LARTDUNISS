package service;

import org.springframework.lang.NonNull;
import model.Cliente;
import repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Cliente guardarCliente(@NonNull Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorId(@NonNull Long id) {
        return clienteRepository.findById(id);
    }
}