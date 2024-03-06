package it.danielecagnoni.mangoio.controllers;

import it.danielecagnoni.mangoio.entities.User;
import it.danielecagnoni.mangoio.exceptions.BadRequestException;
import it.danielecagnoni.mangoio.payloads.NewUserDTO;
import it.danielecagnoni.mangoio.services.AuthSRV;
import it.danielecagnoni.mangoio.services.UserSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserCTRL {
    @Autowired
    private UserSRV userSRV;

    @Autowired
    private AuthSRV authSRV;

    @GetMapping
    @PreAuthorize("hasAuthority('USER' , 'ADMIN')")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return this.userSRV.getUsers(page, size, orderBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER' , 'ADMIN')")
    public User getUserById(@PathVariable UUID id) {
        return this.userSRV.getUserById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUserById(@PathVariable UUID id, @RequestBody @Validated NewUserDTO updatedUser, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.userSRV.updateUserById(updatedUser, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //  Status Code 204
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUserById(@PathVariable UUID id) {
        this.userSRV.deleteUser(id);
    }

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User getMeAndUpdate(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated NewUserDTO updatedUser, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.userSRV.updateUserById(updatedUser, currentAuthenticatedUser.getId());
    }


}
