package io.github.jeffreyihesinulo.moviewebsite.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "director")
public class DirectorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="name")
    private String name;

    @OneToMany(mappedBy = "director")
    private List<MovieEntity> movies = new ArrayList<>();


    public DirectorEntity(String name)
    {
        this.name = name;
    }


}
