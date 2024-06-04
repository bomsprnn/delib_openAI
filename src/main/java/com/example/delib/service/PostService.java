package com.example.delib.service;

import com.example.delib.domain.Member;
import com.example.delib.domain.Post;
import com.example.delib.repository.MemberRepository;
import com.example.delib.repository.PostRepository;
import com.example.delib.service.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PostRepository postRepository;


    /**
     * post 조회
     */
    public Post getPostById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 Post ID 입니다."));
        return post;
    }


    /**
     * post 작성
     */
    public Long writePost(PostDto postDto){
        String username = memberService.getCurrentUser();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .member(member)
                .build();

        return postRepository.save(post).getId();
    }

    /**
     * post 수정
     */
    public Long updatePost(Long postId, PostDto postDto){
        String username = memberService.getCurrentUser();
        Post post = getPostById(postId);
        Member member = post.getMember();
        if (member.getUsername().equals(username)){
            post.update(postDto);
            return postRepository.save(post).getId();
        }
        else throw new IllegalArgumentException("해당 게시글의 작성자가 아닙니다.");
    }

    /**
     *  post 삭제
     */
    public boolean deletePost(Long postId){
        String username = memberService.getCurrentUser();
        Post post = getPostById(postId);
        Member member = post.getMember();
        if (member.getUsername().equals(username)){
            postRepository.delete(post);
            return true;
        }
        else throw new IllegalArgumentException("해당 게시글의 작성자가 아닙니다.");
    }

    /**
     * post 전체 조회
     */
    public List<Post> findAll(Pageable pageable){
        return postRepository.findAll();
    }

}
