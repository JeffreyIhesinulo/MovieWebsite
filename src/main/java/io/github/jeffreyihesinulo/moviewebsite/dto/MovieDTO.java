package io.github.jeffreyihesinulo.moviewebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    Long id;

    String movieName;
    String description;
    String director;
    String language;

    List<String> actors;
    List<String> genres;

    double rating;
    double duration;

    Date releaseDate;


}
