package it.danielecagnoni.mangoio.services;

import it.danielecagnoni.mangoio.entities.MyManga;
import it.danielecagnoni.mangoio.entities.MyReadVolume;
import it.danielecagnoni.mangoio.entities.User;
import it.danielecagnoni.mangoio.exceptions.NotFoundException;
import it.danielecagnoni.mangoio.payloads.MyMangaDTO;
import it.danielecagnoni.mangoio.payloads.MyReadVolumeDTO;
import it.danielecagnoni.mangoio.repositories.MyMangaDAO;
import it.danielecagnoni.mangoio.repositories.MyReadVolumeDAO;
import it.danielecagnoni.mangoio.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class MyMangaSRV {
    @Autowired
    private MyMangaDAO myMangaDAO;
    @Autowired
    private UserSRV userSRV;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MyReadVolumeDAO myReadVolumeDAO;


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
        Set<MyManga> userMangas = user.getMyMangas();
        for (MyManga myManga : userMangas) {
            Double totalVolumesForManga = myManga.getVolumes();
            Long totalVolumesForUserManga = myReadVolumeDAO.countByUserAndManga(user, myManga);
            myManga.setRead(totalVolumesForUserManga.longValue() == totalVolumesForManga);
        }
        return userMangas;
    }


    public void addReadVolumeForManga(MyReadVolumeDTO payload, UUID userId) {
        MyManga foundManga = getMyMangaById(payload.mangaId());
        User foundUser = userSRV.getUserById(userId);

        MyReadVolume existingVolume = myReadVolumeDAO.findByUserAndMangaAndVolNumber(foundUser, foundManga, payload.volNumber());
        if (existingVolume != null) {
            throw new RuntimeException("Volume already exists for manga: " + foundManga.getId() + ", volume number: " + payload.volNumber());
        } else {
            MyReadVolume newVolume = new MyReadVolume();
            newVolume.setVolNumber(payload.volNumber());
            newVolume.setUser(foundUser);
            newVolume.setManga(foundManga);
            myReadVolumeDAO.save(newVolume);
        }
    }


    public Set<MyReadVolume> getMyReadVolumes(Long mangaId, UUID userId) {
        MyManga foundManga = getMyMangaById(mangaId);
        User foundUser = userSRV.getUserById(userId);
        return myReadVolumeDAO.findByMangaAndUser(foundManga, foundUser);
    }

}
