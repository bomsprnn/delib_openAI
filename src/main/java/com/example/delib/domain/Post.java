package com.example.delib.domain;

import com.example.delib.service.dto.PostDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id", updatable = false, unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;  //멤버와 연관관계

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    private String title;
    private String content;

    public void update(PostDto postDto){
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
    }

}

