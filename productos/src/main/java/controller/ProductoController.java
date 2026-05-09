package controller;

import model.Producto;
import service.ProductoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener la lista de todos los productos 
    @GetMapping
    public List<Producto> listar() {
        return productoService.obtenerTodos();
    }

    // Guardar un nuevo producto
    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    // Buscar un producto específico por su ID <3
    @GetMapping("/{id}")
    public Producto obtenerUno(@PathVariable Long id) {
        return productoService.buscarPorId(id).orElse(null);
    }
}