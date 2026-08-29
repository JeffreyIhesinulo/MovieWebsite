package com.example.moviewebsite.Controller;

import com.example.moviewebsite.Service.MovieService;
import com.example.moviewebsite.dto.GenreDTO;
import com.example.moviewebsite.dto.MovieDTO;
import com.example.moviewebsite.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/movies")
public class MovieController {
    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    public MovieController(MovieService movieService)
    {
        this.movieService = movieService;
    }

    //methods
    @GetMapping({"/{id}"})

    public ResponseEntity<MovieDTO> getMovieById(
        @PathVariable("id") Long id
    )
    {
        try {
            log.info("Called getMovieById: id = "+ id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(movieService.getMovieById(id));

        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies()
    {
        try {
            log.info("Called getAllMovies");
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovies());
        }
        catch (NoResultException e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDTO>> getAllgenres()
    {
        try {
            log.info("Called getAllGenres");
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllGenres());
        }
        catch (NoResultException e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

//    @PostMapping
//    public ResponseEntity<MovieDTO> createMovieDTO(
//            @RequestBody MovieDTO movieToCreate
//    )
//    {
//        log.info("Called createMovieDTO");
//        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovieDTO(movieToCreate));
//    }






}
