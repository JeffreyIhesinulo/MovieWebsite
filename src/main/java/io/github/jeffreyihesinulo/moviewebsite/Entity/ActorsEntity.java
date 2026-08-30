package io.github.jeffreyihesinulo.moviewebsite.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "actor")
@Data
@NoArgsConstructor
public class ActorsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public ActorsEntity(String name)
    {
        this.name = name;
    }
}
