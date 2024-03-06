package it.danielecagnoni.mangoio.services;

import it.danielecagnoni.mangoio.entities.User;
import it.danielecagnoni.mangoio.entities.UserRole;
import it.danielecagnoni.mangoio.exceptions.BadRequestException;
import it.danielecagnoni.mangoio.exceptions.NotFoundException;
import it.danielecagnoni.mangoio.payloads.NewUserDTO;
import it.danielecagnoni.mangoio.payloads.UserLoginDTO;
import it.danielecagnoni.mangoio.repositories.UserDAO;
import it.danielecagnoni.mangoio.repositories.UserRoleDAO;
import it.danielecagnoni.mangoio.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthSRV {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserSRV userSRV;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserRoleDAO userRoleDAO;


    public String authUserAndGenerateToken(UserLoginDTO payload) {
        User user = userSRV.findUserByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new RuntimeException("Error logging the user in: incorrect credentials");
        }

    }

    public User saveUser(NewUserDTO payload) {
        userDAO.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("Email " + user.getEmail() + " already in use!");
        });
        UserRole userRole = userRoleDAO.findByRole("USER").orElseThrow(() -> new NotFoundException("Role not found"));

        User newUser = new User(
                payload.email(),
                payload.username(),
                bcrypt.encode(payload.password()),
                payload.name(),
                payload.surname()
        );

        newUser.getRoles().add(userRole);

        return userDAO.save(newUser);
    }
}
