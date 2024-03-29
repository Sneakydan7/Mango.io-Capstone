package it.danielecagnoni.mangoio.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRoleDTO(
        @NotBlank(message = "Role cannot be blank")
        @Size(min = 3, message = "Role must be at least 3 characters")
        String role
) {
}
