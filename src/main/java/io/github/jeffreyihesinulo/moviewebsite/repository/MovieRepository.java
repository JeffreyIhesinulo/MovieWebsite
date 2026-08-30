package io.github.jeffreyihesinulo.moviewebsite.repository;

import io.github.jeffreyihesinulo.moviewebsite.Entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    public List<MovieEntity> findByGenres_Name(String genreName);
}
