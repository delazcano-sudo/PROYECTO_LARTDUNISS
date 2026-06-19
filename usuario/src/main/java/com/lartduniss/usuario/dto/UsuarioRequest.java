package com.lartduniss.usuario.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest 
{
    private String nombreUsuario;
    private String clave;
    private String correo;

    private Set<String> roles;

}
