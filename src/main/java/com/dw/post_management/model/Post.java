package com.dw.post_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Posts")
public class Post {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(unique = true, length = 64, nullable = false)
    private String title;

    @Column(length = 256, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime postedDate;

    private LocalDateTime lastUpdatedDate;

    private LocalDateTime deletedDate;


}
