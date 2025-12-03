package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.domain.Article;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
// @RestController
public class BlogRestController {

    private final BlogService blogService;

    // 예전 REST용 코드
    // @PostMapping("/api/articles")
    // public ResponseEntity<Article> addArticle(@ModelAttribute AddArticleRequest request) {
    //     Article savedArticle = blogService.save(request);
    //     return ResponseEntity
    //             .status(HttpStatus.CREATED)
    //             .body(savedArticle);
    // }

    // 브라우저가 요청하는 /favicon.ico에 대해 빈 응답 처리
    @GetMapping("/favicon.ico")
    public void favicon() {
    }
}
