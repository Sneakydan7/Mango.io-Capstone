package it.danielecagnoni.mangoio.services;

import it.danielecagnoni.mangoio.entities.MyManga;
import it.danielecagnoni.mangoio.entities.User;
import it.danielecagnoni.mangoio.exceptions.NotFoundException;
import it.danielecagnoni.mangoio.payloads.MyMangaDTO;
import it.danielecagnoni.mangoio.repositories.MyMangaDAO;
import it.danielecagnoni.mangoio.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MyMangaSRV {
    @Autowired
    private MyMangaDAO myMangaDAO;
    @Autowired
    private UserSRV userSRV;
    @Autowired
    private UserDAO userDAO;


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

    public Page<MyManga> getMyMangasByScore(int pageNum, int size, String orderBy, String orderDirection) {
        if (size > 100) size = 100;

        Sort.Direction direction = Sort.Direction.fromString(orderDirection);
        Pageable pageable = PageRequest.of(pageNum, size, direction, orderBy);

        return myMangaDAO.findByScoreLessThanEqual(10, pageable);
    }


    public void setMangaForUser(MyMangaDTO mangaId, User user) {
        MyManga found = getMyMangaById(mangaId.id());
        User foundUser = userSRV.getUserById(user.getId());
        foundUser.addMyManga(found);
        userDAO.save(foundUser);
    }

    public void removeMangaForUser(Long mangaId, User user) {
        MyManga found = getMyMangaById(mangaId);
        User foundUser = userSRV.getUserById(user.getId());
        foundUser.removeMangaFromList(found);
        userDAO.save(foundUser);
    }

    public Set<MyManga> getUserMangas(User user) {
        return user.getMyMangas();
    }

}
