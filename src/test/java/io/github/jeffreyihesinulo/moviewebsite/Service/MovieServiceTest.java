package io.github.jeffreyihesinulo.moviewebsite.Service;

import io.github.jeffreyihesinulo.moviewebsite.Entity.ActorsEntity;
import io.github.jeffreyihesinulo.moviewebsite.Entity.DirectorEntity;
import io.github.jeffreyihesinulo.moviewebsite.Entity.GenreEntity;
import io.github.jeffreyihesinulo.moviewebsite.Entity.MovieEntity;
import io.github.jeffreyihesinulo.moviewebsite.dto.MovieDTO;
import io.github.jeffreyihesinulo.moviewebsite.repository.GenreRepository;
import io.github.jeffreyihesinulo.moviewebsite.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    MovieService movieService;

    @Test
    void getMovieById_found_returnsDto() {
        GenreEntity genre = new GenreEntity("Action");
        ActorsEntity actor = new ActorsEntity("Actor One");

        MovieEntity movie = MovieEntity.builder()
                .id(1L)
                .name("Test Movie")
                .description("A test description")
                .language("English")
                .rating(8.5)
                .duration(120)
                .releaseDate(new Date())
                .genres(List.of(genre))
                .actors(List.of(actor))
                .build();

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        MovieDTO dto = movieService.getMovieById(1L);

        assertThat(dto.getMovieName()).isEqualTo("Test Movie");
        assertThat(dto.getGenres()).containsExactly("Action");
        assertThat(dto.getActors()).containsExactly("Actor One");
        assertThat(dto.getDirector()).isNull(); // director is not specified
    }

    @Test
    void getMovieById_notFound_throwsException() {
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> movieService.getMovieById(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getAllMovies_returnsMappedList() {
        MovieEntity movie1 = MovieEntity.builder().id(1L).name("Movie A").build();
        MovieEntity movie2 = MovieEntity.builder().id(2L).name("Movie B").build();

        when(movieRepository.findAll()).thenReturn(List.of(movie1, movie2));

        List<MovieDTO> result = movieService.getAllMovies();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(MovieDTO::getMovieName)
                .containsExactly("Movie A", "Movie B");
    }

    @Test
    void movieEntityToDto_withDirector_mapsDirectorName() {
        DirectorEntity director = new DirectorEntity();
        director.setName("Some Director");

        MovieEntity movie = MovieEntity.builder()
                .id(1L)
                .name("Movie With Director")
                .director(director)
                .build();

        MovieDTO dto = movieService.movieEntityToDto(movie);

        assertThat(dto.getDirector()).isEqualTo("Some Director");
    }
}