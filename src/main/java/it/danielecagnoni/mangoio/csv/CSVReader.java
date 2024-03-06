package it.danielecagnoni.mangoio.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import it.danielecagnoni.mangoio.entities.MyManga;
import it.danielecagnoni.mangoio.repositories.MyMangaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Configuration
public class CSVReader {

    @Autowired
    private MyMangaDAO myMangaDAO;

    public CSVReader() throws FileNotFoundException {
    }

    @Bean
    public List<MyManga> readMangaCSV() throws IOException {
        FileReader file = new FileReader("C:\\Users\\danie\\OneDrive\\Documents\\GitHub\\Mango.io-Capstone\\src\\main\\java\\it\\danielecagnoni\\mangoio\\files\\mal_manga_df.csv");
        List<MyManga> beans = new CsvToBeanBuilder<MyManga>(file)
                .withType(MyManga.class)
                .withSeparator(',')
                .withSkipLines(1)
                .build().parse();
        if (myMangaDAO != null) {
            for (MyManga manga : beans) {
                MyManga newManga = new MyManga(manga.getTitle(), manga.getMalId(), manga.getType(), manga.getChapters(), manga.getVolumes(), manga.getStatus(), manga.getGenres(), manga.getScore(), manga.getRank(), manga.getAuthors(), manga.getSerializations());
                myMangaDAO.save(newManga);
            }
        }
        return beans;
    }
}
