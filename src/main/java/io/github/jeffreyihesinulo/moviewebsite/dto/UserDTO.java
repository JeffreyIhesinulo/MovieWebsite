package io.github.jeffreyihesinulo.moviewebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    Long id;
    String userFirstName;
    String userSecondName;
    String userName;
    String userEmail;
}
