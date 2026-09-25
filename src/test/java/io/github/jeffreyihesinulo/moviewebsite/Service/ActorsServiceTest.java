package io.github.jeffreyihesinulo.moviewebsite.Service;

import io.github.jeffreyihesinulo.moviewebsite.Entity.ActorsEntity;
import io.github.jeffreyihesinulo.moviewebsite.dto.ActorsDTO;
import io.github.jeffreyihesinulo.moviewebsite.repository.ActorsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorsServiceTest {

    @Mock
    ActorsRepository actorsRepository;

    @InjectMocks
    ActorsService actorsService;

    @Test
    void getActorsById_found_returnsDto() {
        ActorsEntity actor = new ActorsEntity("Test Actor");
        actor.setId(1L);

        when(actorsRepository.findById(1L)).thenReturn(Optional.of(actor));

        ActorsDTO dto = actorsService.getActorsById(1L);

        assertThat(dto.getName()).isEqualTo("Test Actor");
    }

    @Test
    void getActorsById_notFound_throwsException() {
        when(actorsRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> actorsService.getActorsById(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getAllActors_returnsMappedList() {
        ActorsEntity actor1 = new ActorsEntity("Actor A");
        ActorsEntity actor2 = new ActorsEntity("Actor B");

        when(actorsRepository.findAll()).thenReturn(List.of(actor1, actor2));

        List<ActorsDTO> result = actorsService.getAllActors();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(ActorsDTO::getName)
                .containsExactly("Actor A", "Actor B");
    }
}