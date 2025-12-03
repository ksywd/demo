package com.example.demo.model.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "testdb")
@Data   // 기본적인 getter/setter 제공
public class TestDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 자동 증가
    private Long id;

    @Column(nullable = true)
    private String name;
}
