package io.github.jeffreyihesinulo.moviewebsite;

import io.github.jeffreyihesinulo.moviewebsite.Entity.UserEntity;
import io.github.jeffreyihesinulo.moviewebsite.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserRegisterControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    private String registerJson(String userName, String email) {
        return """
            {"userName":"%s","firstName":"Test","secondName":"User","email":"%s","password":"secret123"}
            """.formatted(userName, email);
    }

    @Test
    void register_createsUser() throws Exception {
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson("tester1", "tester1@test.com")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("tester1"));
    }

    @Test
    void register_duplicateUserName_returnsConflict() throws Exception {
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson("dupeuser", "first@test.com")))
                .andExpect(status().isCreated());

        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson("dupeuser", "second@test.com")))
                .andExpect(status().isConflict());
    }

    @Test
    void register_duplicateEmail_returnsConflict() throws Exception {
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson("userone", "dupe@test.com")))
                .andExpect(status().isCreated());

        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson("usertwo", "dupe@test.com")))
                .andExpect(status().isConflict());
    }

    @Test
    void register_passwordIsHashedNotStoredPlainText() throws Exception {
        String rawPassword = "secret123";

        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"userName":"hashcheck","firstName":"Test","secondName":"User","email":"hashcheck@test.com","password":"%s"}
                    """.formatted(rawPassword)))
                .andExpect(status().isCreated());

        UserEntity saved = userRepository.findByEmail("hashcheck@test.com").orElseThrow();

        // Пароль НЕ должен храниться как есть
        assertThat(saved.getPasswordHash()).isNotEqualTo(rawPassword);

        // BCrypt-хеш имеет узнаваемый вид: начинается с $2a$, $2b$ или $2y$
        assertThat(saved.getPasswordHash()).startsWith("$2");
    }
}