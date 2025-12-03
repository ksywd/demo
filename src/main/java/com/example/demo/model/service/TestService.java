package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.domain.TestDB;
import com.example.demo.model.repository.TestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

    @Autowired   // TestRepository 주입
    private TestRepository testRepository;

    // 이름으로 데이터 조회
    public TestDB findByName(String name) {
        return testRepository.findByName(name);
    }
}
