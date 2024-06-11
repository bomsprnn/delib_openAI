package com.example.delib.service;

import com.example.delib.JWT.JwtProvider;
import com.example.delib.JWT.JwtToken;
import com.example.delib.domain.Member;
import com.example.delib.repository.MemberRepository;
import com.example.delib.service.dto.MemberDto;
import com.example.delib.service.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtToken login(String username, String password) {
        if (!username.contains("@"))
            throw new IllegalArgumentException("이메일은 @을 포함해야 합니다.");

        if (password.length() < 8)
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");

        memberRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        //Optional<Member> member = memberRepository.findByUsername(username);
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info(authenticationManagerBuilder.getObject().toString());

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken tokenInfo = jwtProvider.generateToken(authentication);

        return tokenInfo;
    }

    @Transactional
    public MemberDto signUp(SignUpDto signUpDto) {
        String username = signUpDto.getUsername();
        String password = signUpDto.getPassword();
        if (!username.contains("@"))
            throw new IllegalArgumentException("이메일은 @을 포함해야 합니다.");

        if (password.length() < 8)
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");

        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }

        // Password 암호화
        String encodedPassword = passwordEncoder.encode(password);

        List<String> roles = new ArrayList<>();
        roles.add("USER");  // USER 권한 부여

        return MemberDto.toDto(memberRepository.save(signUpDto.toEntity(encodedPassword, roles)));
    }

    @Transactional
    public void logout(String token) {
        jwtProvider.logout(token);
    }

    @Transactional
    public void logoutAllDevices(String username) {
        Optional<Member> memberOpt = memberRepository.findByUsername(username);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            member.setLastLogout(new Date());
            memberRepository.save(member);
        } else {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }
}
