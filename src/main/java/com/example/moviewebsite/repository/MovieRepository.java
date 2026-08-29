package com.example.moviewebsite.repository;

import com.example.moviewebsite.Entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    public List<MovieEntity> findByGenres_Name(String genreName);
}
