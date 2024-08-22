package com.dw.post_management.controller;

import com.dw.post_management.model.Post;
import com.dw.post_management.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    ResponseEntity<?> addPost(@RequestBody Post post) {
        Post _post = postService.addPost(post);
        if (post != null) {
            return ResponseEntity.ok(_post);
        } else {
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
