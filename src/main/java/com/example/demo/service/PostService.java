package com.example.demo.service;

import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(PostRequest request, User author) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(author)
                .build();
        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPostList(Pageable pageable) {
        return postRepository.findAllWithAuthor(pageable).map(PostResponse::from);
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return PostResponse.from(post);
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest request, User user) throws AccessDeniedException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 권한 확인
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new AccessDeniedException("게시글 수정 권한이 없습니다.");
        }

        post.update(request.getTitle(), request.getContent());
        return PostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long postId, User user) throws AccessDeniedException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 권한 확인
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new AccessDeniedException("게시글 삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }
}