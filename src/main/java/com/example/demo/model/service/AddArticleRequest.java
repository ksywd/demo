package com.example.demo.model.service;

import lombok.*;
import com.example.demo.model.domain.Board;

@NoArgsConstructor
@AllArgsConstructor
@Data
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

    // board 테이블의 나머지 NOT NULL 컬럼들
    private String mobile;
    private String name;
    private String password;

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
