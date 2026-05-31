package controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import model.Reportes;
import service.ReportesService;

@RestController
@RequestMapping("/api/reportes")
public class ReportesController {

    @Autowired
    private ReportesService reportesService;

    @GetMapping
    public ResponseEntity<List<Reportes>> listar() {
        List<Reportes> lista = reportesService.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Reportes> crear(@Valid @RequestBody Reportes reporte) {
        Reportes nuevo = reportesService.guardarReporte(reporte);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reportes> obtenerUno(@PathVariable Long id) {
        return reportesService.buscarPorId(id)
                .map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reportes> actualizar(@PathVariable Long id, @Valid @RequestBody Reportes reporte) {
        return reportesService.actualizarReporte(id, reporte)
                .map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (reportesService.eliminarReporte(id)) {
            return new ResponseEntity<>("Reporte eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encontró el reporte a eliminar", HttpStatus.NOT_FOUND);
    }
}