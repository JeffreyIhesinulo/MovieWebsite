package com.example.moviewebsite.Service;

import com.example.moviewebsite.Entity.ActorEntity;
import com.example.moviewebsite.Entity.GenreEntity;
import com.example.moviewebsite.Entity.MovieEntity;
import com.example.moviewebsite.dto.GenreDTO;
import com.example.moviewebsite.dto.MovieDTO;
import com.example.moviewebsite.repository.GenreRepository;
import com.example.moviewebsite.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;


    public MovieService(MovieRepository movieRepository, GenreRepository genreRepository)
    {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    //Get Movie by ID
    public MovieDTO getMovieById(Long id)
    {
        MovieEntity movieEntity = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found movie by id = " + id
                ));
        return movieEntityToDto(movieEntity);


    }

    //Get all movies method
    public List<MovieDTO> getAllMovies()
    {
        List<MovieEntity> movieEntitieList = movieRepository.findAll();
        return  movieEntitieList.stream().map(this::movieEntityToDto).toList();
    }


    //Create Movie
//    public MovieDTO createMovieDTO(MovieDTO movieToCreate)
//    {
//        if (movieToCreate.getId() != null)
//        {
//            throw new IllegalArgumentException("Id should be empty!");
//        }
//        MovieEntity newMovieEntity= new MovieEntity(
//                null,
//                movieToCreate.getMovieName(),
//                movieToCreate.getReleaseDate()
//        );
//        //important to pass the saved value to return because .save method is changing the id from null to generated
//        return movieEntityToDto(movieRepository.save(newMovieEntity));
//    }

    //Get all genres
    public List<GenreDTO> getAllGenres()
    {
        List<GenreEntity> allGenresList =  genreRepository.findAll();
        return allGenresList.stream().map(this::genreToDto).toList();

    }


    //Method to turn the Entity ti DTO
    public MovieDTO movieEntityToDto(MovieEntity movieEntity)
    {
        List<String> genres = movieEntity.getGenres()
                .stream()
                .map(GenreEntity::getName)
                .toList(); // getting genres from movie entity
        List<String> actors = movieEntity
                .getActors()
                .stream()
                .map(ActorEntity::getName)
                .toList();// getting actors from movie entity

        String director = movieEntity.getDirector()
                != null
                ? movieEntity.getDirector().getName()
                : null;
        String description = movieEntity.getDescription()
                != null
                ? movieEntity.getDescription()
                : null;

       return new MovieDTO(
                movieEntity.getId(),
                movieEntity.getName(),
                description,
                movieEntity.getReleaseDate(),
                actors,
                director,
                movieEntity.getLanguage(),
                movieEntity.getRating(),
                movieEntity.getDuration(),
                genres

        );

    }

    public GenreDTO genreToDto(GenreEntity genreEntity)
    {
        return new GenreDTO(
                genreEntity.getId(),
                genreEntity.getName()
        );
    }

}
