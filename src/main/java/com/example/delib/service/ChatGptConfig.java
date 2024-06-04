package com.example.delib.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChatGptConfig {

    @Value("${chatgpt.api-key}")
    private String apiKey;
    @Bean  // 요청을 보내기 위해 Resttemplete 설정, bean 에 등록
    public RestTemplate restTemplate(){
        RestTemplate template = new RestTemplate(); //resttemplete 인스턴스 생성
        template.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add( //요청 헤더에 authorization 추가,
                    // 모든 요청에 대해 open api key를 포함시킴
                    "Authorization"
                    ,"Bearer "+apiKey);
            return execution.execute(request,body);
        });

        return template;

    }

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String CHAT_MODEL = "gpt-3.5-turbo";
    public static final Integer MAX_TOKEN = 300;
    public static final Boolean STREAM = false;
    public static final String ROLE = "user";
    public static final Double TEMPERATURE = 0.6;
    //public static final Double TOP_P = 1.0;
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    //completions : 질답
    public static final String CHAT_URL = "https://api.openai.com/v1/chat/completions";
}

