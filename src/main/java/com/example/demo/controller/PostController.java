package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @Valid @RequestBody PostRequest request,
            @AuthenticationPrincipal User user) {
        PostResponse postResponse = postService.createPost(request, user);
        return ResponseEntity.ok(ApiResponse.success(postResponse));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getPostList(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponse> postList = postService.getPostList(pageable);
        return ResponseEntity.ok(ApiResponse.success(postList));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(
            @PathVariable Long postId) {
        PostResponse postResponse = postService.getPostById(postId);
        return ResponseEntity.ok(ApiResponse.success(postResponse));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest request,
            @AuthenticationPrincipal User user) throws AccessDeniedException {
        PostResponse postResponse = postService.updatePost(postId, request, user);
        return ResponseEntity.ok(ApiResponse.success(postResponse));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User user) throws AccessDeniedException {
        postService.deletePost(postId, user);
        return ResponseEntity.ok(ApiResponse.success());
    }
}