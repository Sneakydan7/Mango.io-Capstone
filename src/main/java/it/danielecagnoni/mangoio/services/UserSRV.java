package it.danielecagnoni.mangoio.services;

import it.danielecagnoni.mangoio.entities.User;
import it.danielecagnoni.mangoio.exceptions.UUIDNotFoundException;
import it.danielecagnoni.mangoio.payloads.NewUserDTO;
import it.danielecagnoni.mangoio.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserSRV {
    @Autowired
    private UserDAO userDAO;

    public Page<User> getUsers(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);

    }

    public User getUserById(UUID id) {
        return userDAO.findById(id).orElseThrow(() -> new UUIDNotFoundException(id));
    }

    public User updateUserById(NewUserDTO updatedUser, UUID id) {
        User found = getUserById(id);
        found.setName(updatedUser.name());
        found.setSurname(updatedUser.surname());
        found.setEmail(updatedUser.email());
        found.setUsername(updatedUser.username());
        found.setPassword(updatedUser.password());
        userDAO.save(found);
        return found;
    }

    public void deleteUser(UUID id) {
        User found = getUserById(id);
        userDAO.delete(found);
    }

    public User findUserByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new RuntimeException("User with this email not found"));

    }
}
