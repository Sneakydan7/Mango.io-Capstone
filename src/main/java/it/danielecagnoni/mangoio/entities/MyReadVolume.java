package it.danielecagnoni.mangoio.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "volumes")
@Getter
@Setter
@NoArgsConstructor
public class MyReadVolume {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private Double volNumber;
    @JsonIgnore
    @ManyToOne
    private MyManga manga;
    @JsonIgnore
    @ManyToOne
    private User user;


}
