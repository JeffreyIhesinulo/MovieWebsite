package io.github.jeffreyihesinulo.moviewebsite.Controller;

import io.github.jeffreyihesinulo.moviewebsite.Service.UserService;
import io.github.jeffreyihesinulo.moviewebsite.security.AuthResponseDTO;
import io.github.jeffreyihesinulo.moviewebsite.dto.UserDTO;
import io.github.jeffreyihesinulo.moviewebsite.dto.UserRegisterDTO;
import io.github.jeffreyihesinulo.moviewebsite.dto.UserSignInDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {
    private final UserService userService;
    public static final Logger log = LoggerFactory.getLogger(UserController.class);


    public UserController(UserService userService)
    {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> userRegistration(@RequestBody UserRegisterDTO dto)
    {
        log.info("Called userRegistration");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.userRegistration(dto));
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponseDTO> userSignIn(@RequestBody UserSignInDTO dto)
    {
        log.info("Called userSignIn");
        return ResponseEntity.status(HttpStatus.OK).body(userService.userSignIn(dto));
    }
}
