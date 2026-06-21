package service;

import model.Cliente;
import repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void guardarClienteExitosoTest() {
        // 1. Arrange
        Cliente clienteInput = new Cliente(null, "Denisse Lazcano", "denisse@lartduniss.com", "+56912345678");
        Cliente clienteGuardado = new Cliente(1L, "Denisse Lazcano", "denisse@lartduniss.com", "+56912345678");

        Mockito.when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteGuardado);

        // 2. Act
        Cliente resultado = clienteService.guardarCliente(clienteInput);

        // 3. Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("denisse@lartduniss.com", resultado.getEmail());
        Mockito.verify(clienteRepository, Mockito.times(1)).save(clienteInput);
    }

    @Test
    void buscarClientePorIdTest() {
        // 1. Arrange
        Long clienteId = 1L;
        Cliente clienteMock = new Cliente(clienteId, "Denisse Lazcano", "denisse@lartduniss.com", "+56912345678");
        Mockito.when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));

        // 2. Act
        Optional<Cliente> resultado = clienteService.buscarPorId(clienteId);

        // 3. Assert
        assertEquals(true, resultado.isPresent());
        assertEquals("Denisse Lazcano", resultado.get().getNombre());
        Mockito.verify(clienteRepository, Mockito.times(1)).findById(clienteId);
    }
}