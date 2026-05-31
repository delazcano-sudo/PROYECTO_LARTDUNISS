package controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import model.Despacho;
import service.DespachoService;

@RestController
@RequestMapping("/api/despachos") 
public class DespachoController {

    @Autowired
    private DespachoService despachoService;

    @GetMapping
    public ResponseEntity<List<Despacho>> listar() {
        List<Despacho> lista = despachoService.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Despacho> crear(@Valid @RequestBody Despacho despacho) {
        Despacho nuevo = despachoService.guardarDespacho(despacho);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despacho> obtenerUno(@PathVariable Long id) {
        return despachoService.buscarPorId(id)
                .map(d -> new ResponseEntity<>(d, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despacho> actualizar(@PathVariable Long id, @Valid @RequestBody Despacho despacho) {
        return despachoService.actualizarDespacho(id, despacho)
                .map(d -> new ResponseEntity<>(d, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (despachoService.eliminarDespacho(id)) {
            return new ResponseEntity<>("Despacho eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encontró el despacho a eliminar", HttpStatus.NOT_FOUND);
    }
}