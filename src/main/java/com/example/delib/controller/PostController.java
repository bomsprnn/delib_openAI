package com.example.delib.controller;

import com.example.delib.domain.Post;
import com.example.delib.service.PostService;
import com.example.delib.service.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
// @RequestMapping("/post")  // set the basic path
public class PostController {
    private final PostService postService;

    /**
     * 게시물 작성
     */
    @PostMapping("/post/create")
    public Long createPost(PostDto postDto){
        return postService.writePost(postDto);
    }

    /**
     * 게시물 상세 조회
     */
    @GetMapping("/post/{postId}")
    public Post postDetail(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    /**
     * 게시물 수정
     */
    @PutMapping("/post/put/{postId}")
    public Long udpatePost(@PathVariable Long postId, PostDto postDto){
        return postService.updatePost(postId, postDto);
    }

    /**
     * 게시물 삭제
     */
    @DeleteMapping("/post/delete/{postId}")
    public boolean deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }

    /**
     * 게시물 리스트 조회
     */
    @GetMapping("/post/list")
    public List<Post> findAll(Pageable pageable){
        return postService.findAll(pageable);
    }
}
