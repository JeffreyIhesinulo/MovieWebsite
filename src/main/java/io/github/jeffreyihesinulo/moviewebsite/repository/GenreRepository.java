package io.github.jeffreyihesinulo.moviewebsite.repository;

import io.github.jeffreyihesinulo.moviewebsite.Entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    Optional<GenreEntity> findByName(String name);
}
