package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private LocalDate BIRTHDAY_DATE;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        BIRTHDAY_DATE = LocalDate.of(1982, 04, 07);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void createWrongLoginUser() throws Exception {

        User user = new User(1, "aa@bb.com", "log in", "name", BIRTHDAY_DATE);
        String requestBody = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(post("/users")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError());

    }

    @Test
    void updateUser() {
    }
}