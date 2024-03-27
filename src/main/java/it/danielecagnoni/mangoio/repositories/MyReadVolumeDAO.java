package it.danielecagnoni.mangoio.repositories;

import it.danielecagnoni.mangoio.entities.MyManga;
import it.danielecagnoni.mangoio.entities.MyReadVolume;
import it.danielecagnoni.mangoio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MyReadVolumeDAO extends JpaRepository<MyReadVolume, Long> {

    public Set<MyReadVolume> findByMangaAndUser(MyManga manga, User user);
}
