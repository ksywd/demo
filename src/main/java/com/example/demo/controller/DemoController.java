package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.TestService;

@Controller
public class DemoController {

    @Autowired
    private TestService testService;

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("data", "방갑습니다.");
        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2(Model model) {
        model.addAttribute("name", "홍길동");
        model.addAttribute("greeting", "방갑습니다.");
        model.addAttribute("today", "오늘");
        model.addAttribute("weather", "날씨는");
        model.addAttribute("comment", "매우 좋습니다.");
        return "hello2";
    }

    @GetMapping("/about_detailed")
    public String about() {
        return "about_detailed";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/test1")
    public String thymeleaf_test1(Model model) {
        model.addAttribute("data1", "<h2> 방갑습니다 </h2>");
        model.addAttribute("data2", "태그의 속성 값");
        model.addAttribute("link", 01);
        model.addAttribute("name", "홍길동");
        model.addAttribute("para1", "001");
        model.addAttribute("para2", 002);
        return "thymeleaf_test1";
    }

    // testdb 예제 페이지
    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {

        TestDB user1 = testService.findByName("홍길동");
        TestDB user2 = testService.findByName("아저씨");
        TestDB user3 = testService.findByName("꾸러기");

        model.addAttribute("user1", user1);
        model.addAttribute("user2", user2);
        model.addAttribute("user3", user3);

        System.out.println("데이터 출력 디버그 : " + user1 + ", " + user2 + ", " + user3);

        return "testdb";
    }
}
