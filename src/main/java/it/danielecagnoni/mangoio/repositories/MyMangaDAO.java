package it.danielecagnoni.mangoio.repositories;

import it.danielecagnoni.mangoio.entities.MyManga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyMangaDAO extends JpaRepository<MyManga, Long> {
    Optional<MyManga> findByMalId(Integer malId);

    Page<MyManga> findByTitleContainingIgnoreCase(String title,
                                                  Pageable pageable);

    Page<MyManga> findByScoreLessThanEqual(double score, Pageable pageable);
}
