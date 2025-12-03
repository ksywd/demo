package com.example.demo.model.service;

import lombok.*;
import com.example.demo.model.domain.Board;

@NoArgsConstructor
@AllArgsConstructor
@Data   // 기본적인 getter/setter 제공
public class AddArticleRequest {

    private String title;
    private String content;
    private String user;
    private String newdate;
    private String count;
    private String likec;
    private String address;
    private String age;
    private String email;

    // board 테이블의 나머지 필드들
    private String mobile;
    private String name;
    private String password;

    // 입력값을 Board 엔티티로 변환
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .newdate(newdate)
                .count(count)
                .likec(likec)
                .address(address)
                .age(age)
                .email(email)
                .mobile(mobile)
                .name(name)
                .password(password)
                .build();
    }
}
