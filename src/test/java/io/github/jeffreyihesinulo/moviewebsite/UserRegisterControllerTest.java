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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        assertThat(saved.getPasswordHash()).isNotEqualTo(rawPassword);

        assertThat(saved.getPasswordHash()).startsWith("$2");
    }

    @Test
    void signIn_returnsToken() throws Exception {
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson("loginuser", "loginuser@test.com")))
                .andExpect(status().isCreated());

        mvc.perform(post("/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"userEmail":"loginuser@test.com","password":"secret123"}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void signIn_wrongPassword_returnsUnauthorized() throws Exception {
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson("loginuser2", "loginuser2@test.com")))
                .andExpect(status().isCreated());

        mvc.perform(post("/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"userEmail":"loginuser2@test.com","password":"wrongpass"}
                """))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getMovies_withoutToken_returnsForbidden() throws Exception {
        mvc.perform(get("/movies"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getMovies_withValidToken_returnsOk() throws Exception {
        // Регистрируем и логинимся, чтобы получить настоящий токен
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson("securitycheck", "securitycheck@test.com")))
                .andExpect(status().isCreated());

        String signInResponse = mvc.perform(post("/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"userEmail":"securitycheck@test.com","password":"secret123"}
                """))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = com.jayway.jsonpath.JsonPath.read(signInResponse, "$.token");

        mvc.perform(get("/movies")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}