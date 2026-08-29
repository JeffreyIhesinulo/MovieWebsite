package com.example.moviewebsite.repository;

import com.example.moviewebsite.Entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActorsRepository extends JpaRepository<ActorEntity, Long> {
    Optional<ActorEntity> findByName(String name);
}
