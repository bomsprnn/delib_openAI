package com.example.delib.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class QuestionRequestDto implements Serializable {
    private String target;
    private String content;
    private boolean words;
    private String detail;


    @Builder
    private QuestionRequestDto(String target, String content, boolean words,String detail){
        this.target=target;
        this.content=content;
        this.words=words;
        this.detail=detail;
    }
}
