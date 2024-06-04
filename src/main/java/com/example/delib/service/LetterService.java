package com.example.delib.service;

import com.example.delib.repository.LetterRepository;
import com.example.delib.service.dto.GptRequest;
import com.example.delib.service.dto.GptResponse;
import com.example.delib.service.dto.QuestionRequestDto;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class LetterService {

    private final ChatgptService chatgptService;
    private final LetterRepository letterRepository;

    private final RestTemplate restTemplate;
    @Value("${chatgpt.api-key}")
    private String apiKey;
    @Value("${chatgpt.model}")
    private String model;
    @Value("${chatgpt.url}")
    private String apiUrl;

    //             "temperature": 1,
//             "max_tokens": 256,
//             "top_p": 1,
//             "frequency_penalty": 0,
//             "presence_penalty": 0
    //
    public String writeLetter(QuestionRequestDto requestDto) {
        String prompt = buildQuestion(requestDto);
        GptRequest request = new GptRequest(model, prompt, 1, 256, 1, 1, 0);
        GptResponse gptResponse = restTemplate.postForObject(
                apiUrl
                , request
                , GptResponse.class
        );

        return gptResponse.getChoices().get(0).getMessage().getContent();


    }

    public String buildQuestion(QuestionRequestDto requestDto) {
        StringBuilder questionBuilder = new StringBuilder();

        // Add the target to the question if it is not null or empty
        if (requestDto.getTarget() != null && !requestDto.getTarget().isEmpty()) {
            questionBuilder.append(requestDto.getTarget()).append("에게 ");
        }

        // Add the content to the question if it is not null or empty
        if (requestDto.getContent() != null && !requestDto.getContent().isEmpty()) {
            questionBuilder.append(requestDto.getContent()).append("하기 위해 보내는 편지를 ");
        }

        // Add the words to the question if true
        if (requestDto.isWords()) {
            questionBuilder.append("존댓말로 작성해줘.");
        }
        if (!requestDto.isWords()) {
            questionBuilder.append("반말로 작성해줘.");

        }
        if (requestDto.getDetail() != null && !requestDto.getDetail().isEmpty()) {
            questionBuilder.append(requestDto.getDetail()).append(" 부탁해.");
        }
        String prompt = questionBuilder.toString().trim();
        return prompt;
    }

}