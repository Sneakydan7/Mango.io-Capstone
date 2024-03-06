package it.danielecagnoni.mangoio.controllers;

import it.danielecagnoni.mangoio.entities.User;
import it.danielecagnoni.mangoio.exceptions.BadRequestException;
import it.danielecagnoni.mangoio.payloads.LoginResponseDTO;
import it.danielecagnoni.mangoio.payloads.NewUserDTO;
import it.danielecagnoni.mangoio.payloads.UserLoginDTO;
import it.danielecagnoni.mangoio.services.AuthSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthCTRL {
    @Autowired
    public AuthSRV authSRV;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Validated UserLoginDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new LoginResponseDTO(authSRV.authUserAndGenerateToken(payload));

    }

    @PostMapping("/register")
    public User register(@RequestBody @Validated NewUserDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.authSRV.saveUser(payload);
    }
}
