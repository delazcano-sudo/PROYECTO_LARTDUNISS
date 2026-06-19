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
@Tag(name = "Autentication", description = "Endpoits para registro y logi de usuarios")
public class UsuarioController
{
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Registrar un nuevo usuario", description = "Guarda el usuario mapeando sus roles desde el DTO")
    @PostMapping("/registrar")
    // CAMBIO CLAVE: Usamos AuthRequest en lugar de la Entidad Usuario
    public ResponseEntity<String> registrar(@RequestBody UsuarioRequest request)
    {
        // Le pasamos el objeto request (DTO) al service
        return ResponseEntity.ok(usuarioService.registrar(request));
    }

    @Operation(summary = "Iniciar sesión", description = "Retorna el Token JWT si las credenciales son válidas")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioRequest request)
    {
        try {
            String token = usuarioService.login(request.getNombreUsuario(), request.getClave());
            return ResponseEntity.ok(token);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}