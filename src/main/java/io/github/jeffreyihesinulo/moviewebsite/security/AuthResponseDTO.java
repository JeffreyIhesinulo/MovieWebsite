package io.github.jeffreyihesinulo.moviewebsite.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    String token;
    Long id;
    String userEmail;
    String userName;

}
