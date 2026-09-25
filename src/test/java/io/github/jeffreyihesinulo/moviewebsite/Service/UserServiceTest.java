package io.github.jeffreyihesinulo.moviewebsite.Service;

import io.github.jeffreyihesinulo.moviewebsite.Entity.Role;
import io.github.jeffreyihesinulo.moviewebsite.Entity.UserEntity;
import io.github.jeffreyihesinulo.moviewebsite.dto.UserSignInDTO;
import io.github.jeffreyihesinulo.moviewebsite.repository.UserRepository;
import io.github.jeffreyihesinulo.moviewebsite.security.AuthResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    JwtService jwtService;

    @InjectMocks
    UserService userService;

    UserEntity existingUser;
    String rawPassword = "secret123";

    @BeforeEach
    void setUp() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        existingUser = UserEntity.builder()
                .id(1L)
                .email("user@test.com")
                .userName("tester")
                .firstName("Test")
                .secondName("User")
                .passwordHash(encoder.encode(rawPassword))
                .role(Role.USER)
                .build();
    }

    @Test
    void signIn_correctCredentials_returnsAuthResponse() {
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(existingUser));
        when(jwtService.generateToken("user@test.com", 1L, Role.USER)).thenReturn("fake-jwt-token");

        AuthResponseDTO response = userService.userSignIn(new UserSignInDTO("user@test.com", rawPassword));

        assertThat(response.getToken()).isEqualTo("fake-jwt-token");
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUserEmail()).isEqualTo("user@test.com");
        assertThat(response.getUserName()).isEqualTo("tester");
    }

    @Test
    void signIn_wrongPassword_throwsBadCredentials() {
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(existingUser));

        assertThatThrownBy(() ->
                userService.userSignIn(new UserSignInDTO("user@test.com", "wrongpassword"))
        ).isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void signIn_emailNotFound_throwsBadCredentials() {
        when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                userService.userSignIn(new UserSignInDTO("missing@test.com", "whatever"))
        ).isInstanceOf(BadCredentialsException.class);
    }
}