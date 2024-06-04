package com.example.delib.controller;

import com.example.delib.JWT.JwtToken;
import com.example.delib.service.MemberService;
import com.example.delib.service.dto.MemberDto;
import com.example.delib.service.dto.SignInDto;
import com.example.delib.service.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/join")
    public ResponseEntity<MemberDto> signUp(@RequestBody SignUpDto signUpDto) {
    MemberDto savedMemberDto = memberService.signUp(signUpDto);
    return ResponseEntity.ok(savedMemberDto);
}
    @PostMapping("/login")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String username = signInDto.getUsername();
        String password = signInDto.getPassword();
        log.info("hiiii"+username);
        JwtToken jwtToken = memberService.login(username, password);
        log.info(username,"그리고+"+password);
        log.info("hiiii");

        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/user/test")
    public String test() {
        return "success";
    }
    @PostMapping("admin/tsts")
    public String test3() {
        Authentication authentication;
        authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getUsername();
    }

    @PostMapping("/test")
    public String test2() {
        return "success";
    }

}
