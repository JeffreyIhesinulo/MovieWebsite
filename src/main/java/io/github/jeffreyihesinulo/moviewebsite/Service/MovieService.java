package io.github.jeffreyihesinulo.moviewebsite.Service;

import io.github.jeffreyihesinulo.moviewebsite.Entity.ActorsEntity;
import io.github.jeffreyihesinulo.moviewebsite.Entity.GenreEntity;
import io.github.jeffreyihesinulo.moviewebsite.Entity.MovieEntity;
import io.github.jeffreyihesinulo.moviewebsite.dto.GenreDTO;
import io.github.jeffreyihesinulo.moviewebsite.dto.MovieDTO;
import io.github.jeffreyihesinulo.moviewebsite.repository.GenreRepository;
import io.github.jeffreyihesinulo.moviewebsite.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
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
                .map(ActorsEntity::getName)
                .toList();// getting actors from movie entity

        String director = movieEntity.getDirector()
                != null
                ? movieEntity.getDirector().getName()
                : null;
        String description = movieEntity.getDescription()
                != null
                ? movieEntity.getDescription()
                : null;

       return MovieDTO.builder()
               .id(movieEntity.getId())
               .movieName(movieEntity.getName())
               .description(movieEntity.getDescription())
               .director(director)
               .language(movieEntity.getLanguage())
               .actors(actors)
               .genres(genres)
               .rating(movieEntity.getRating())
               .duration(movieEntity.getDuration())
               .releaseDate(movieEntity.getReleaseDate())
               .build();

    }

    public GenreDTO genreToDto(GenreEntity genreEntity)
    {
        return new GenreDTO(
                genreEntity.getId(),
                genreEntity.getName()
        );
    }

}
