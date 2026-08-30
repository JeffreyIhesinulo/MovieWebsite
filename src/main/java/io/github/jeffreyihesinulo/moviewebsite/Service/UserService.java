package io.github.jeffreyihesinulo.moviewebsite.Service;

import io.github.jeffreyihesinulo.moviewebsite.Entity.UserEntity;
import io.github.jeffreyihesinulo.moviewebsite.dto.UserDTO;
import io.github.jeffreyihesinulo.moviewebsite.dto.UserRegisterDTO;
import io.github.jeffreyihesinulo.moviewebsite.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public UserDTO userRegistration(UserRegisterDTO registerDTO)
    {
        if(userRepository.existsByUserName(registerDTO.getUserName()))
        {
            throw new IllegalArgumentException("Username is already taken");
        }
        if(userRepository.existsByEmail(registerDTO.getEmail()))
        {
            throw new IllegalArgumentException("Email is already taken");
        }
        String hashedPassword = passwordEncoder.encode(registerDTO.getPassword());

        UserEntity userEntity = UserEntity.builder()
                .userName(registerDTO.getUserName().strip())
                .email(registerDTO.getEmail().strip())
                .firstName(registerDTO.getFirstName().strip())
                .passwordHash(hashedPassword)
                .id(registerDTO.getId())
                .secondName(registerDTO.getSecondName().strip()).build();

        UserEntity savedUser = userRepository.save(userEntity);

        return new UserDTO(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getSecondName(),
                savedUser.getUserName(),
                savedUser.getEmail()
        );
    }
}
