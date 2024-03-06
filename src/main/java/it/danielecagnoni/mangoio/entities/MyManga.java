package it.danielecagnoni.mangoio.entities;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mangas")
@Getter
@Setter
@NoArgsConstructor
public class MyManga {

    @CsvBindByPosition(position = 0)
    public String title;
    @CsvBindByPosition(position = 1)
    public Integer malId;
    @CsvBindByPosition(position = 2)
    public String type;
    @CsvBindByPosition(position = 3)
    public Double chapters;
    @CsvBindByPosition(position = 4)
    public Double volumes;
    @CsvBindByPosition(position = 5)
    public String status;
    @CsvBindByPosition(position = 6)
    public String genres;
    @CsvBindByPosition(position = 7)
    public double score;
    @CsvBindByPosition(position = 8)
    public Double rank;
    @CsvBindByPosition(position = 9)
    public String authors;
    @CsvBindByPosition(position = 10)
    public String serializations;
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    public MyManga(String title, Integer malId, String type, Double chapters, Double volumes, String status, String genres, double score, Double rank, String authors, String serializations) {
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
