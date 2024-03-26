package it.danielecagnoni.mangoio.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "mangas")
@Getter
@Setter
@NoArgsConstructor
public class MyManga {

    @CsvBindByPosition(position = 0)
    private String title;
    @CsvBindByPosition(position = 1)
    private Integer malId;
    @CsvBindByPosition(position = 2)
    private String type;
    @CsvBindByPosition(position = 3)
    private Double chapters;
    @CsvBindByPosition(position = 4)
    private Double volumes;
    @CsvBindByPosition(position = 5)
    private String status;
    @CsvBindByPosition(position = 6)
    private String genres;
    @CsvBindByPosition(position = 7)
    private Double score;
    @CsvBindByPosition(position = 8)
    private Double rank;
    @CsvBindByPosition(position = 9)
    private String authors;
    @CsvBindByPosition(position = 10)
    private String serializations;

    private String imageUrl;
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(mappedBy = "myMangas", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users = new LinkedHashSet<>();


    public MyManga(String title, Integer malId, String type, Double chapters, Double volumes, String status, String genres, Double score, Double rank, String authors, String serializations) {
        this.title = title;
        this.malId = malId;
        this.type = type;
        this.chapters = chapters;
        this.volumes = volumes;
        this.status = status;
        this.genres = genres;
        this.score = score;
        this.rank = rank;
        this.authors = authors;
        this.serializations = serializations;
    }
}
