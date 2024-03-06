package it.danielecagnoni.mangoio.repositories;

import it.danielecagnoni.mangoio.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleDAO extends JpaRepository<UserRole, UUID> {
    Optional<UserRole> findByRole(String role);
}
