package io.github.jeffreyihesinulo.moviewebsite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegisterControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void register_createsUser() throws Exception {
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"userName":"tester","firstName":"Test","secondName":"User","email":"tester@test.com","password":"secret123"}
                    """))
                .andExpect(status().isCreated());
    }
}