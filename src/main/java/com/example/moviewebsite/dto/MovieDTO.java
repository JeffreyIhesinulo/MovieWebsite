package com.example.moviewebsite.dto;

import com.example.moviewebsite.Entity.GenreEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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


    public MovieDTO(Long id,
                    String name,
                    String description,
                    Date releaseDate,
                    List<String> actors,
                    String director,
                    String language,
                    double rating,
                    double duration,
                    List<String> genres)
    {
       this.id = id;
       this.movieName = name;
       this.description = description;
       this.actors = actors;
       this.director = director;
       this.duration = duration;
       this.language = language;
       this.rating = rating;
       this.releaseDate = releaseDate;
       this.genres = genres;
    }

}
