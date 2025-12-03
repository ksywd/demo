package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title = "";

    @Column(nullable = false)
    private String content = "";

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 제목, 내용 수정용 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
