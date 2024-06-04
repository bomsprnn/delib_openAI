package com.example.delib.controller;

import com.example.delib.service.LetterService;
import com.example.delib.service.dto.QuestionRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class LetterController {
    private final LetterService letterService;


    /**
     * 편지글 작성
     */
    @PostMapping("/letter")
    public String makeNewLetter(@RequestBody QuestionRequestDto requestDto) {
        return letterService.writeLetter(requestDto);
    }


}
