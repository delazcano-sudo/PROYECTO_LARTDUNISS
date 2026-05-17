package com.lartduniss.usuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lartduniss.usuario.model.Usuario;
import com.lartduniss.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService
{
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos()
    {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id)
    {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) 
    {
        if(usuario.getRol() == null || usuario.getRol().isEmpty())
            {
                usuario.setRol("ROL_CLIENTE");
            }
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id)
    {
        usuarioRepository.deleteById(id);
    }
    

}
