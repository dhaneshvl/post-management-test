package com.dw.post_management.service;

import com.dw.post_management.exception.UserNotFoundException;
import com.dw.post_management.model.Post;
import com.dw.post_management.repo.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PostService {

    private final List<Long> VALID_USER_IDS = List.of(1L, 2L);

    @Autowired
    PostRepository postRepository;

    public Post addPost(Post post) {
        boolean isValidUser = VALID_USER_IDS.stream()
                .noneMatch(userId -> Objects.equals(userId, post.getUserId()));

        if (isValidUser) {
            throw new UserNotFoundException("Invalid User");
        }

        post.setPostedDate(post.getPostedDate() != null ? post.getPostedDate() : LocalDateTime.now());

        return postRepository.save(post);
    }
}
