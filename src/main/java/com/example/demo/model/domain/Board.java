package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title = "";

    @Column(name = "content", nullable = false)
    private String content = "";

    @Column(name = "user", nullable = false)
    private String user = "";

    @Column(name = "newdate", nullable = false)
    private String newdate = "";

    @Column(name = "count", nullable = false)
    private String count = "";

    @Column(name = "likec", nullable = false)
    private String likec = "";

    @Column(name = "address", nullable = false)
    private String address = "";

    @Column(name = "age", nullable = false)
    private String age = "";

    @Column(name = "email", nullable = false)
    private String email = "";

    // === 여기부터 추가된 컬럼들 ===

    @Column(name = "mobile", nullable = false)
    private String mobile = "";

    @Column(name = "name", nullable = false)
    private String name = "";

    @Column(name = "password", nullable = false)
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

        this.address  = (address  == null) ? "" : address;
        this.age      = (age      == null) ? "" : age;
        this.email    = (email    == null) ? "" : email;
        this.mobile   = (mobile   == null) ? "" : mobile;
        this.name     = (name     == null) ? "" : name;
        this.password = (password == null) ? "" : password;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
