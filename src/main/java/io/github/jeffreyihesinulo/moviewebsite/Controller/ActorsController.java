package io.github.jeffreyihesinulo.moviewebsite.Controller;

import io.github.jeffreyihesinulo.moviewebsite.Service.ActorsService;
import io.github.jeffreyihesinulo.moviewebsite.dto.ActorsDTO;
import jakarta.persistence.NoResultException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorsController {
    public static final Logger log = LoggerFactory.getLogger(ActorsController.class);
    private final ActorsService actorsService;
    public ActorsController(ActorsService actorsService)
    {
        this.actorsService = actorsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorsDTO> getActorsById(@PathVariable("id") Long id)
    {
        try {
            log.info("Called getActorsById");
            return ResponseEntity.status(HttpStatus.OK).body(actorsService.getActorsById(id));
        }
        catch (EntityActionVetoException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ActorsDTO>> getAllActors()
    {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(actorsService.getAllActors());
        }
        catch (
                NoResultException e
        )
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

}
