package com.dw.post_management;

import com.dw.post_management.controller.PostController;
import com.dw.post_management.exception.UserNotFoundException;
import com.dw.post_management.model.Post;
import com.dw.post_management.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PostService postService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturnOkResponseForCreatePost() throws Exception {

        Post newPost = Post.builder()
                .id(1L)
                .title("Post 1")
                .postedDate(LocalDateTime.now())
                .content("Hi this is a test content for post 1.")
                .userId(1L).build();

        String newPostRequest = objectMapper.writeValueAsString(newPost);

        when(postService.addPost(newPost)).thenReturn(newPost);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/posts").contentType(MediaType.APPLICATION_JSON)
                        .content(newPostRequest))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Post returnedPost = objectMapper.readValue(jsonResponse, Post.class);

        assertEquals(newPost, returnedPost);

    }

    @Test
    void shouldThrowUserNotFoundExceptionForInvalidUserId() throws Exception {

        Post newPost = Post.builder()
                .id(1L)
                .title("Post 1")
                .postedDate(LocalDateTime.now())
                .content("Hi this is a test content for post 1.")
                .userId(-1L).build();

        String newPostRequest = objectMapper.writeValueAsString(newPost);

        when(postService.addPost(newPost)).thenThrow(new UserNotFoundException("Invalid User"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPostRequest))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        String responseMessage = mvcResult.getResponse().getContentAsString();
        String expectedErrorMessage = "Invalid User";

        assertEquals(expectedErrorMessage, responseMessage);

    }

}
