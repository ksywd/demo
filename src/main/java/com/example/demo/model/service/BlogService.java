package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BlogRepository;
import com.example.demo.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final BoardRepository blogRepository2;    // 실제 게시판 기능에 사용

    // 전체 게시글 조회
    public List<Board> findAll() {
        return blogRepository2.findAll();
    }

    // 게시글 저장
    public Board save(AddArticleRequest request) {
        return blogRepository2.save(request.toEntity());
    }

    // 특정 게시글 조회
    public Optional<Board> findById(Long id) {
        return blogRepository2.findById(id);
    }

    // 게시글 수정
    public void update(Long id, AddArticleRequest request) {
        blogRepository2.findById(id).ifPresent(board -> {
            board.update(request.getTitle(), request.getContent());
            blogRepository2.save(board);
        });
    }

    // 게시글 삭제
    public void delete(Long id) {
        blogRepository2.deleteById(id);
    }

    // 페이징 조회
    public Page<Board> findAll(Pageable pageable) {
        return blogRepository2.findAll(pageable);
    }

    // 제목 검색
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        return blogRepository2.findByTitleContainingIgnoreCase(keyword, pageable);
    }
}
