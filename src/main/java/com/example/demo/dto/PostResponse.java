package com.example.demo.dto;

import com.example.demo.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String authorNickname; // 작성자 닉네임 추가
    private LocalDateTime createdAt;

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getNickname(), // 지연 로딩 주의
                post.getCreatedAt()
        );
    }
}