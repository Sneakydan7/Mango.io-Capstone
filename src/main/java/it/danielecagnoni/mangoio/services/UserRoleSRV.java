package it.danielecagnoni.mangoio.services;

import it.danielecagnoni.mangoio.entities.UserRole;
import it.danielecagnoni.mangoio.exceptions.BadRequestException;
import it.danielecagnoni.mangoio.exceptions.NotFoundException;
import it.danielecagnoni.mangoio.payloads.UserRoleDTO;
import it.danielecagnoni.mangoio.repositories.UserRoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRoleSRV {
    @Autowired
    private UserRoleDAO userRoleDAO;

    public Page<UserRole> getUserRoles(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return userRoleDAO.findAll(pageable);
    }

    public UserRole saveRole(UserRoleDTO newRole) {
        userRoleDAO.findByRole(newRole.role().toUpperCase()).ifPresent(userRole -> {
            throw new BadRequestException("Role " + newRole.role().toUpperCase() + " already exist!");
        });
        UserRole userRole = new UserRole(newRole.role().toUpperCase());
        return userRoleDAO.save(userRole);
    }

    public UserRole getUserRoleById(UUID id) {
        return userRoleDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteRole(UUID id) {
        UserRole found = getUserRoleById(id);
        userRoleDAO.delete(found);
    }
}
