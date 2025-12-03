package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 게시글 번호 자동 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title = "";

    @Column(nullable = false)
    private String content = "";

    @Column(nullable = false)
    private String user = "";

    @Column(nullable = false)
    private String newdate = "";

    @Column(nullable = false)
    private String count = "";     // 조회수

    @Column(nullable = false)
    private String likec = "";     // 좋아요 수

    @Column(nullable = false)
    private String address = "";

    @Column(nullable = false)
    private String age = "";

    @Column(nullable = false)
    private String email = "";

    // 추가 정보 컬럼들
    @Column(nullable = false)
    private String mobile = "";

    @Column(nullable = false)
    private String name = "";

    @Column(nullable = false)
    private String password = "";

    @Builder
    public Board(String title,
                 String content,
                 String user,
                 String newdate,
                 String count,
                 String likec,
                 String address,
                 String age,
                 String email,
                 String mobile,
                 String name,
                 String password) {

        this.title = title;
        this.content = content;
        this.user = user;
        this.newdate = newdate;
        this.count = count;
        this.likec = likec;

        // 값이 null로 들어오는 경우 대비
        this.address  = (address  == null) ? "" : address;
        this.age      = (age      == null) ? "" : age;
        this.email    = (email    == null) ? "" : email;
        this.mobile   = (mobile   == null) ? "" : mobile;
        this.name     = (name     == null) ? "" : name;
        this.password = (password == null) ? "" : password;
    }

    // 제목과 내용만 수정할 때 사용
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
