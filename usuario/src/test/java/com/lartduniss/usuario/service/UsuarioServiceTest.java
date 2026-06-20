package com.lartduniss.usuario.service;

import com.lartduniss.usuario.model.Usuario;
import com.lartduniss.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void loginCredencialesInvalidasTest() {
        // 1. Arrange
        String username = "denisse";
        String claveFalsa = "123456";
        Mockito.when(usuarioRepository.findByNombreUsuario(username)).thenReturn(Optional.empty());

        // 2. Act & 3. Assert (Patrón AAA)
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            usuarioService.login(username, claveFalsa);
        });

        assertEquals("Credenciales inválidas", excepcion.getMessage());
    }
}