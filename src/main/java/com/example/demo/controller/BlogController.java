package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.domain.Board;
import com.example.demo.model.domain.Article;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    // 게시판 목록
    @GetMapping("/board_list")
    public String board_list(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "") String keyword,
                             HttpSession session) {

        // 로그인 여부 확인
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        System.out.println("세션 userId: " + userId);

        String email = (String) session.getAttribute("email");
        System.out.println("세션 email: " + email);

        int pageSize = 30;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Board> list;

        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable);
        } else {
            list = blogService.searchByKeyword(keyword, pageable);
        }

        int startNum = (page * pageSize) + 1;

        model.addAttribute("boards", list);
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startNum", startNum);
        model.addAttribute("email", email);

        return "board_list";
    }

    // 게시글 상세 페이지
    @GetMapping("/board_view/{id}")
    public String board_view(Model model,
                             @PathVariable Long id,
                             HttpSession session) {

        Optional<Board> list = blogService.findById(id);

        if (list.isPresent()) {
            model.addAttribute("boards", list.get());
        } else {
            return "/error_page/article_error";
        }

        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);

        return "board_view";
    }

    // 글쓰기 페이지
    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }

    // 게시글 저장
    @PostMapping("/api/boards")
    public String addboards(@ModelAttribute AddArticleRequest request,
                            HttpSession session) {

        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }

        request.setUser(email);
        request.setEmail(email);

        blogService.save(request);
        return "redirect:/board_list";
    }

    // 게시글 수정 페이지
    @GetMapping("/board_edit/{id}")
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> boardOpt = blogService.findById(id);

        if (boardOpt.isPresent()) {
            model.addAttribute("board", boardOpt.get());
        } else {
            return "/error_page/article_error";
        }
        return "board_edit";
    }

    // 게시글 수정 처리
    @PostMapping("/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/board_list";
    }

    // 게시글 삭제
    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }

    // 아래부터는 article_list 관련 기존 코드 유지

    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/article_list";
    }

    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list";
    }

    @PostMapping("/api/articles")
    public String addArticle(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/article_list";
    }
}
