package controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import model.Despacho;
import service.DespachoService;

// ANOTACIONES SWAGGER!!!!!!!!!!
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/despachos")
@Tag(name = "Controlador de Despachos", description = "Endpoints para la gestión, actualización y eliminación de envíos")
public class DespachoController {

    @Autowired
    private DespachoService despachoService;

    @GetMapping
    @Operation(summary = "Listar todos los despachos", description = "Retorna una lista completa de todos los despachos registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    public ResponseEntity<List<Despacho>> listar() {
        List<Despacho> lista = despachoService.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo despacho", description = "Registra un despacho en el sistema y valida sus campos")
    @ApiResponse(responseCode = "201", description = "Despacho creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    public ResponseEntity<Despacho> crear(@Valid @RequestBody Despacho despacho) {
        Despacho nuevo = despachoService.guardarDespacho(despacho);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }
// Añadimos el runtime exception para manejar el caso de que no se encuentre el despacho, y así devolver un mensaje más claro al cliente
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un despacho por ID", description = "Busca un despacho específico en la base de datos a partir de su ID")
    @ApiResponse(responseCode = "200", description = "Despacho encontrado")
    @ApiResponse(responseCode = "404", description = "Despacho no encontrado", 
                 content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = exception.ErrorResponse.class)))
    public ResponseEntity<Despacho> obtenerUno(@PathVariable Long id) {
        Despacho despacho = despachoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("El despacho con ID " + id + " no existe en el sistema."));
        return new ResponseEntity<>(despacho, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un despacho existente", description = "Modifica los detalles de un despacho existente buscando por su ID")
    @ApiResponse(responseCode = "200", description = "Despacho actualizado con éxito")
    @ApiResponse(responseCode = "404", description = "Despacho no encontrado para actualizar")
    public ResponseEntity<Despacho> actualizar(@PathVariable Long id, @Valid @RequestBody Despacho despacho) {
        return despachoService.actualizarDespacho(id, despacho)
                .map(d -> new ResponseEntity<>(d, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un despacho", description = "Elimina permanentemente un despacho del sistema a partir de su ID")
    @ApiResponse(responseCode = "200", description = "Despacho eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Despacho no encontrado")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (despachoService.eliminarDespacho(id)) {
            return new ResponseEntity<>("Despacho eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encontró el despacho a eliminar", HttpStatus.NOT_FOUND);
    }
}