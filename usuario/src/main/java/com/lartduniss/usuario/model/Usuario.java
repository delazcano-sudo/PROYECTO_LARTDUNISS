package com.lartduniss.usuario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 8, message = "la contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "El formato del email no es valido")
    private String email;
    
    private String rol;
    //Roles; "ROL_ADMIN" o "Rol_cliente"

}
