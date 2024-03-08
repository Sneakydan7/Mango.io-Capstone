package it.danielecagnoni.mangoio.services;

import it.danielecagnoni.mangoio.entities.MyManga;
import it.danielecagnoni.mangoio.exceptions.NotFoundException;
import it.danielecagnoni.mangoio.repositories.MyMangaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MyMangaSRV {
    @Autowired
    private MyMangaDAO myMangaDAO;


    public MyManga save(MyManga body) {

        return myMangaDAO.save(body);
    }

    public Page<MyManga> getMyMangas(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return myMangaDAO.findAll(pageable);
    }

    public MyManga getMyMangaById(Long id) {
        return myMangaDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public MyManga getMyMangaByMalId(Integer id) {
        return myMangaDAO.findByMalId(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<MyManga> getMyMangaByTitle(String title, int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));

        return myMangaDAO.findByTitleContainingIgnoreCase(title, pageable);
    }
}
