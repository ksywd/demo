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

    public Board toEntity() { // Article 객체 생성
        return Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .newdate(newdate)
                .count(count)
                .likec(likec)
                .build();
    }
}
