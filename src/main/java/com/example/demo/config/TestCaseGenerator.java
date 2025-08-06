package com.example.demo.config;

import com.example.demo.domain.Post;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TestCaseGenerator implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // 유저 생성
        User peter = createTestUser("peter", "abcd1234", "javalab");
        User mary = createTestUser("mary", "qwer1122", "reactor");

        // 게시글 생성
        createTestPosts(peter, 15); // peter가 15개의 게시글 작성
        createTestPosts(mary, 5);  // mary가 5개의 게시글 작성
    }

    /*  login test 용 json

    {
        "username": "peter",
        "password": "abcd1234"
    }

    {
        "username": "mary",
        "password": "qwer1122"
    }

    */

    /* signup test

    {
        "username": "holynight",
        "password": "abcd1234",
        "nickname": "happy323"
    }

    */

    /* post test

    {
        "title": "Hello, Java",
        "content": "java, collection, generic, oop..."
    }

    */


    private User createTestUser(String username, String password, String nickname) {
        // 이미 해당 username의 유저가 존재하면 기존 유저 정보를 반환
        return userRepository.findByUsername(username).orElseGet(() -> {
            User user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .nickname(nickname)
                    .role(Role.USER)
                    .build();
            return userRepository.save(user); // DB에 저장하고 반환
        });
    }

    private void createTestPosts(User author, int count) {
        // 해당 유저가 작성한 글이 이미 있다면 더 생성하지 않음 (필요에 따라 로직 변경 가능)
        if (postRepository.countByAuthor(author) > 0) {
            return;
        }

        for (int i = 1; i <= count; i++) {
            Post post = Post.builder()
                    .title(author.getNickname() + "의 " + i + "번째 게시글")
                    .content(i + "번째 게시글의 내용입니다.")
                    .author(author)
                    .build();
            postRepository.save(post);
        }
    }
}