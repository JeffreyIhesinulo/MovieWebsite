package io.github.jeffreyihesinulo.moviewebsite.Service;

import io.github.jeffreyihesinulo.moviewebsite.Entity.Role;
import io.github.jeffreyihesinulo.moviewebsite.Entity.UserEntity;
import io.github.jeffreyihesinulo.moviewebsite.security.AuthResponseDTO;
import io.github.jeffreyihesinulo.moviewebsite.dto.UserDTO;
import io.github.jeffreyihesinulo.moviewebsite.dto.UserRegisterDTO;
import io.github.jeffreyihesinulo.moviewebsite.dto.UserSignInDTO;
import io.github.jeffreyihesinulo.moviewebsite.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService)
    {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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
                .secondName(registerDTO.getSecondName().strip())
                .role(Role.USER).build();

        UserEntity savedUser = userRepository.save(userEntity);

        return new UserDTO(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getSecondName(),
                savedUser.getUserName(),
                savedUser.getEmail()
        );
    }


        public AuthResponseDTO userSignIn(UserSignInDTO dto)
        {
            UserEntity userEntity = userRepository.findByEmail(dto.getUserEmail())
                    .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

            if(!passwordEncoder.matches(dto.getPassword(), userEntity.getPasswordHash()))
            {
                throw new BadCredentialsException("Invalid email or password");
            }

            String token = jwtService.generateToken(userEntity.getEmail(), userEntity.getId(), userEntity.getRole());

            return AuthResponseDTO.builder()
                    .token(token)
                    .id(userEntity.getId())
                    .userEmail(userEntity.getEmail())
                    .userName(userEntity.getUserName())
                    .build();

        }
}
