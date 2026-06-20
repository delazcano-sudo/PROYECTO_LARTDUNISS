package com.lartduniss.usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lartduniss.usuario.dto.UsuarioRequest;
import com.lartduniss.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Authentication", description = "Endpoints para registro e inicio de sesión de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Registrar un nuevo usuario", description = "Guarda el usuario asignándole por defecto el rol de CLIENTE si no se especifican roles.")
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.registrar(request));
    }

    @Operation(summary = "Iniciar sesión", description = "Retorna el Token JWT correspondiente si las credenciales coinciden")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioRequest request) {
        try {
            String token = usuarioService.login(request.getNombreUsuario(), request.getClave());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}