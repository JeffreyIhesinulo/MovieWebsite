package io.github.jeffreyihesinulo.moviewebsite.Service;

import io.github.jeffreyihesinulo.moviewebsite.Entity.ActorsEntity;
import io.github.jeffreyihesinulo.moviewebsite.dto.ActorsDTO;
import io.github.jeffreyihesinulo.moviewebsite.repository.ActorsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorsService {
    private final ActorsRepository actorsRepository;

    public ActorsService(ActorsRepository actorsRepository)
    {
        this.actorsRepository = actorsRepository;
    }

    public ActorsDTO getActorsById(Long id) {
        ActorsEntity actorsEntity = actorsRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Not found entity id = " + id));
        return actorsEntityToDto(actorsEntity);
    }

    public List<ActorsDTO> getAllActors()
    {
        List<ActorsEntity> actorsEntities = actorsRepository.findAll();
        return actorsEntities.stream().map(this::actorsEntityToDto).toList();
    }

    public ActorsDTO actorsEntityToDto(ActorsEntity actorsEntity)
    {
        return new ActorsDTO(
                actorsEntity.getId(),
                actorsEntity.getName()
        );
    }

}
