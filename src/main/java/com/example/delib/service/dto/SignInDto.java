package com.example.delib.service.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignInDto {
    private String username;
    private String password;
}