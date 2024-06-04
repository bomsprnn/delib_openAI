package com.example.delib;

import com.example.delib.domain.Letter;
import com.example.delib.repository.LetterRepository;
import com.example.delib.service.LetterService;
import com.example.delib.service.dto.QuestionRequestDto;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Rollback(value = false)
@Transactional
class LetterServiceTest {

    @Autowired private ChatgptService chatgptService;
    @Autowired private LetterService letterService;
    @Autowired private LetterRepository letterRepository;
    @Test
    void writeLetter() {
        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setTarget("엄마");
        requestDto.setContent("생일을 축하하기 위해 보내는 편지");
        requestDto.setWords(true);
        requestDto.setDetail("고맙다는 인사 추가");

        letterService.writeLetter(requestDto);
        Letter letter = letterRepository.findLetterById(1L);
        assertEquals(letter.getTarget(), requestDto.getTarget());

    }

    @Test
    void buildQuestion() {
    }
}