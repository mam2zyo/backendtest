package com.example.demo.repository;

import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            value = "SELECT DISTINCT p FROM Post p JOIN FETCH p.author",
            countQuery = "SELECT COUNT(DISTINCT p) FROM Post p")
    Page<Post> findAllWithAuthor(Pageable pageable);

    long countByAuthor(User author);
}
