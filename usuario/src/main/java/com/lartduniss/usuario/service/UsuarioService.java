package com.lartduniss.usuario.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lartduniss.usuario.dto.UsuarioRequest;
import com.lartduniss.usuario.model.Rol;
import com.lartduniss.usuario.model.Usuario;
import com.lartduniss.usuario.repository.RolRepository;
import com.lartduniss.usuario.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired 
    private RolRepository rolRepository;
    
    @org.springframework.beans.factory.annotation.Value("${jwt.secret}")
    private String secreto;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registrar(UsuarioRequest request) {
        if (usuarioRepository.findByNombreUsuario(request.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe.");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(request.getNombreUsuario());
        nuevoUsuario.setCorreo(request.getCorreo());
        nuevoUsuario.setClave(passwordEncoder.encode(request.getClave()));

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            Rol rolPorDefecto = rolRepository.findByNombreRol("PACIENTE")
                    .orElseThrow(() -> new RuntimeException("Error: El rol PACIENTE no existe en la DB."));
            nuevoUsuario.getRoles().add(rolPorDefecto);
        } else {
            for (String nombreRol : request.getRoles()) {
                Rol rolEncontrado = rolRepository.findByNombreRol(nombreRol.toUpperCase())
                        .orElseThrow(() -> new RuntimeException("Error: El rol " + nombreRol + " no existe en la DB."));
                nuevoUsuario.getRoles().add(rolEncontrado);
            }
        }

        usuarioRepository.save(nuevoUsuario);
        return "Usuario Registrado";
    }

    // 🌟 AGREGAR ESTA ANOTACIÓN AQUÍ 🌟
    // Hace que Hibernate mantenga la sesión abierta para leer la tabla 'usuario_roles' de la base de datos
    @Transactional(readOnly = true)
    public String login(String nombreUsuario, String clave) {
    
        // 1. Buscar al usuario
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        // 2. Verificar la contraseña
        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // 3. Extraer los nombres de los roles (Ahora sí vendrá ["ADMINISTRADOR"])
        List<String> rolesList = usuario.getRoles().stream()
                .map(rol -> rol.getNombreRol())
                .collect(Collectors.toList());

        // 4. Configurar fechas explícitas
        java.util.Date ahora = new java.util.Date();
        java.util.Date expiracion = new java.util.Date(ahora.getTime() + 86400000); // 24 horas

        // 5. Generar el Token con los roles de la Base de Datos
        return Jwts.builder()
                .setSubject(usuario.getNombreUsuario()) 
                .claim("roles", rolesList)
                .setIssuedAt(ahora)                    
                .setExpiration(expiracion)              
                .signWith(Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256) 
                .compact();
    }
}