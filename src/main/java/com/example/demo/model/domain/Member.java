package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name = "";

    @Column(unique = true, nullable = false) // 이메일 중복 방지
    private String email = "";

    @Column(nullable = false)
    private String password = "";

    @Column(nullable = false)
    private String age = "";

    @Column(nullable = false)
    private String mobile = "";

    @Column(nullable = false)
    private String address = "";

    @Builder
    public Member(String name, String email, String password,
                  String age, String mobile, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
    }
}
