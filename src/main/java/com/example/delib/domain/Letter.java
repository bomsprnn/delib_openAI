package com.example.delib.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Letter {
    @Id
    @GeneratedValue
    @Column(name = "letter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;  //멤버와 연관관계

    private String target;
    private String content;
    private boolean words;
    private String detail;
    //대상, 목적, 어투(1이면 존댓말 0이면 반말),
    // 특이사항 등 편지 관련 정보

    private String result;

}
