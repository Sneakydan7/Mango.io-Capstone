package it.danielecagnoni.mangoio.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class UserRole {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;


    private String role;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();


    public UserRole(String role) {
        this.role = role;
    }
}