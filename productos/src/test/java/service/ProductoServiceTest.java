package service;

import model.Producto;
import repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void guardarProductoExitosoTest() {
        // 1. Arrange
        Producto productoInput = new Producto();
        productoInput.setNombre("Pastel de Chocolate");
        productoInput.setPrecio(25000.0);
        productoInput.setStock(10);

        Producto productoGuardado = new Producto();
        productoGuardado.setId(1L);
        productoGuardado.setNombre("Pastel de Chocolate");
        productoGuardado.setPrecio(25000.0);
        productoGuardado.setStock(10);

        Mockito.when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        // 2. Act
        Producto resultado = productoService.guardar(productoInput);

        // 3. Assert (Patrón AAA)
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Pastel de Chocolate", resultado.getNombre());
        Mockito.verify(productoRepository, Mockito.times(1)).save(productoInput);
    }
}