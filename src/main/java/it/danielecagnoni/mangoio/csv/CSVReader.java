package it.danielecagnoni.mangoio.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import it.danielecagnoni.mangoio.entities.MyManga;
import it.danielecagnoni.mangoio.entities.MyMangaImage;
import it.danielecagnoni.mangoio.repositories.MyMangaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Configuration
public class CSVReader {

    @Autowired
    private MyMangaDAO myMangaDAO;

    public CSVReader() throws FileNotFoundException {
    }

    @Bean
    public List<MyManga> readMangaCSV() throws IOException {
        FileReader file = new FileReader("C:\\Users\\danie\\OneDrive\\Documents\\GitHub\\Mango.io-Capstone\\src\\main\\java\\it\\danielecagnoni\\mangoio\\files\\mal_manga_df.csv");
        FileReader imageFile = new FileReader("C:\\Users\\danie\\OneDrive\\Documents\\GitHub\\Mango.io-Capstone\\src\\main\\java\\it\\danielecagnoni\\mangoio\\files\\manga_covers.csv");
        List<MyManga> mangaList = new CsvToBeanBuilder<MyManga>(file)
                .withType(MyManga.class)
                .withSeparator(',')
                .withSkipLines(1)
                .build().parse();


        List<MyMangaImage> mangaImageList = new CsvToBeanBuilder<MyMangaImage>(imageFile)
                .withType(MyMangaImage.class)
                .withSeparator(',')
                .withSkipLines(1)
                .build().parse();

        if (myMangaDAO == null) {
            for (MyManga manga : mangaList) {
                mangaImageList.stream()
                        .filter(m -> Objects.equals(m.getMalId(), manga.getMalId()))
                        .findFirst()
                        .ifPresent(m -> manga.setImageUrl(m.getUrl()));
                myMangaDAO.save(manga);
            }
        }
        return mangaList;
    }


}
