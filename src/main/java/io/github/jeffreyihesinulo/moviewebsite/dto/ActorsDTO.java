package io.github.jeffreyihesinulo.moviewebsite.dto;

import lombok.Data;

@Data
public class ActorsDTO {
    Long id;
    String name;

    public ActorsDTO(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

}
