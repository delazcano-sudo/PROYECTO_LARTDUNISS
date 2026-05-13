package com.lartduniss.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lartduniss.usuario.model.Usuario;
import com.lartduniss.usuario.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController 
{
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listar()
    {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id)
    {
        return usuarioService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario)
    {
        Usuario nuevoUsuario = usuarioService.guardar(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id)
    {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
