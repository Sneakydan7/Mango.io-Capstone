package it.danielecagnoni.mangoio.controllers;

import it.danielecagnoni.mangoio.entities.UserRole;
import it.danielecagnoni.mangoio.exceptions.BadRequestException;
import it.danielecagnoni.mangoio.payloads.UserRoleDTO;
import it.danielecagnoni.mangoio.services.UserRoleSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class UserRoleCTRL {
    @Autowired
    private UserRoleSRV userRoleSRV;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserRole> getRoles(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String orderBy) {
        return this.userRoleSRV.getUserRoles(page, size, orderBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserRole getRoleById(@PathVariable UUID id) {
        return this.userRoleSRV.getUserRoleById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRole saveRole(@RequestBody @Validated UserRoleDTO newRole, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.userRoleSRV.saveRole(newRole);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoleById(@PathVariable UUID id) {
        this.userRoleSRV.deleteRole(id);
    }
}
