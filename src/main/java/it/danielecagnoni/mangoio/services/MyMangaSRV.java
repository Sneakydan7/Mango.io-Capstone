package it.danielecagnoni.mangoio.services;

import it.danielecagnoni.mangoio.entities.MyManga;
import it.danielecagnoni.mangoio.repositories.MyMangaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyMangaSRV {
    @Autowired
    private MyMangaDAO myMangaDAO;


    public MyManga save(MyManga body) {

        return myMangaDAO.save(body);
    }
}
