package com.example.user.controller;

import com.example.user.model.User;
import com.example.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

//@WebMvcTest({UserControllerTest.class})
//@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp(){
        user = new User(
                new ObjectId("642978d763c00e671509e010"),
                "test_user_id",
                "Test User",
                "password",
                "test@example.com");

        Mockito.when(userService.allUser()).thenReturn(Arrays.asList(user));
        Mockito.when(userService.userById("test_user_id")).thenReturn(Optional.of(user));
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenAnswer(invocation -> {
            User newUser = invocation.getArgument(0);
            return newUser;
        });
        Mockito.when(userService.updateUser(Mockito.anyString(), Mockito.any(User.class))).thenReturn(user);
    }

    @Test
    public void testGetAllUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].userId").value("test_user_id")
        );
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users/test_user_id")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.userId").value("test_user_id")
        );
    }

    @Test
    public void testCreateUser() throws Exception{
        User newUser = new User(
                new ObjectId("642978d763c00e671509e011"),
                "new_user_id",
                "New User",
                "new_password",
                "new@example.com"
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newUser))
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.userId").value("new_user_id")
        );
    }

    @Test
    public void testUpdateUser() throws Exception{
        user.setName("User name Updated");

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/v1/users/test_user_id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user))
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("User name Updated")
        );
    }

    @Test
    public void testDeleteUserById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/users/test_user_id")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        // 삭제된 사용자를 조회해봄
        Mockito.when(userService.userById("test_user_id")).thenReturn(Optional.empty());
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users/test_user_id")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

}