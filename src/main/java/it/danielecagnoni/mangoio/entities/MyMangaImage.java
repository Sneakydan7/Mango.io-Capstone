package it.danielecagnoni.mangoio.entities;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyMangaImage {
    @CsvBindByPosition(position = 0)
    private Integer malId;
    @CsvBindByPosition(position = 1)
    private String url;

    public MyMangaImage(Integer malId, String url) {
        this.malId = malId;
        this.url = url;
    }
}
