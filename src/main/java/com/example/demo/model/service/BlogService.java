package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BlogRepository;
import com.example.demo.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final BoardRepository blogRepository2; // 리포지토리 선언

    // 전체 게시글 조회
    // public List<Article> findAll() {
    //     return blogRepository.findAll();
    // }

    public List<Board> findAll() { // 게시판 전체 목록 조회
        return blogRepository2.findAll();
    }


    // 게시글 등록
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    // 게시글 단일 조회
    // public Optional<Article> findById(Long id) {
    //     return blogRepository.findById(id);
    // }

    public Optional<Board> findById(Long id) { // 게시판 특정 글 조회
        return blogRepository2.findById(id);
    }

    // // 게시글 수정
    public void update(Long id, AddArticleRequest request) {
        blogRepository.findById(id).ifPresent(article -> {
            article.update(request.getTitle(), request.getContent());
            blogRepository.save(article);
        });
    }

    // 게시글 삭제
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
