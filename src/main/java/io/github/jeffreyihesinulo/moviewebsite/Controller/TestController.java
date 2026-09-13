package io.github.jeffreyihesinulo.moviewebsite.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;
@RestController
public class TestController {
    @GetMapping("/whoami")
    public ResponseEntity<String> whoAmI() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated())
        {
            return ResponseEntity.ok("No one authenticated");
        }
        return ResponseEntity.ok("Authenticated as: " + auth.getName());

    }

}
