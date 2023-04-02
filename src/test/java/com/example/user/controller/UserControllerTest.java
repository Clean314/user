package com.example.user.controller;

import com.example.user.model.User;
import com.example.user.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

//@WebMvcTest({UserControllerTest.class})
//@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user1, user2;

    @BeforeEach
    void setUp(){
        user1 = new User(
                new ObjectId("642978d763c00e671509e010"),
                "test_user1_id",
                "Test User1",
                "password1",
                "test@example.com");
        user2 = new User(
                new ObjectId("642978e463c00e671509e012"),
                "test_user_id",
                "Test User",
                "password",
                "test@example.com");

        List<User> userList = Arrays.asList(user1, user2);
        Mockito.when(userService.allUser()).thenReturn(userList);
    }

    @Test
    public void testGetAllUser() throws Exception {
        List<User> userList = Arrays.asList(user1, user2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].userId").value("test_user1_id")
        );
    }

}