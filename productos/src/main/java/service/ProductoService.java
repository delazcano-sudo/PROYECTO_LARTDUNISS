package service;

import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import model.Producto;
import repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto guardar(@NonNull Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> buscarPorId(@NonNull Long id) {
        return productoRepository.findById(id);
    }
}