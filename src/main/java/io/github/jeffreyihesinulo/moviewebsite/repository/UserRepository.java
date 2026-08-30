package io.github.jeffreyihesinulo.moviewebsite.repository;

import io.github.jeffreyihesinulo.moviewebsite.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
        boolean existsByUserName(String name);
        boolean existsByEmail(String email);
}
