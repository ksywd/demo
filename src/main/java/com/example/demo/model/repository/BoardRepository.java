package com.example.demo.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 제목에 특정 문자열이 포함된 게시글 검색(대소문자 무시)
    Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
