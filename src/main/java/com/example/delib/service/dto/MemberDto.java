package com.example.delib.service.dto;

import com.example.delib.domain.Member;
import lombok.*;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String username;
    private String nickname;
    private String address;
    private String phone;
    private String profileImg;

    static public MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
               .build();
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .username(username)
                .nickname(nickname)
                .build();
    }
}