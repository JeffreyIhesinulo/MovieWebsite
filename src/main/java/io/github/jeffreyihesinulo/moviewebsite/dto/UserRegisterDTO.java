package io.github.jeffreyihesinulo.moviewebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    Long id;
    String userName;
    String firstName;
    String secondName;
    String email;
    String password;

}
