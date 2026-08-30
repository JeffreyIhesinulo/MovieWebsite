package io.github.jeffreyihesinulo.moviewebsite.repository;

import io.github.jeffreyihesinulo.moviewebsite.Entity.ActorsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorsRepository extends JpaRepository<ActorsEntity, Long> {
    Optional<ActorsEntity> findByName(String name);
}
