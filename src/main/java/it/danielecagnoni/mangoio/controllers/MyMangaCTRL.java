package it.danielecagnoni.mangoio.controllers;

import it.danielecagnoni.mangoio.entities.MyManga;
import it.danielecagnoni.mangoio.services.MyMangaSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mangas")
public class MyMangaCTRL {
    @Autowired
    private MyMangaSRV myMangaSRV;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER' , 'ADMIN')")
    public Page<MyManga> getMangas(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return this.myMangaSRV.getMyMangas(page, size, orderBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER' , 'ADMIN')")
    public MyManga getMyMangaById(@PathVariable Long id) {
        return this.myMangaSRV.getMyMangaById(id);
    }

    @GetMapping("/mal/{malId}")
    @PreAuthorize("hasAnyAuthority('USER' , 'ADMIN')")
    public MyManga getMyMangaByMalId(@PathVariable Integer malId) {
        return this.myMangaSRV.getMyMangaByMalId(malId);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('USER' , 'ADMIN')")
    public Page<MyManga> getMyMangaByTitle(@RequestParam String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return this.myMangaSRV.getMyMangaByTitle(title, page, size, orderBy);
    }

    @GetMapping("/score")
    @PreAuthorize("hasAnyAuthority('USER' , 'ADMIN')")
    public Page<MyManga> getMangasByScore(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size, @RequestParam(defaultValue = "score") String orderBy, @RequestParam(defaultValue = "desc") String orderDirection) {
        return this.myMangaSRV.getMyMangasByScore(page, size, orderBy, orderDirection);
    }


}
