package com.example.delib.service.dto;

import com.example.delib.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;
    private String username;

}
