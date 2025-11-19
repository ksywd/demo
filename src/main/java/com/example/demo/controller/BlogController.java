package com.example.demo.controller;

import java.util.List;
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

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    // @GetMapping("/article_list")
    // public String article_list(Model model) {
    //     List<Article> list = blogService.findAll();
    //     model.addAttribute("articles", list);
    //     return "article_list";
    // }

    // @GetMapping("/board_list") // 새로운 게시판 링크 지정
    // public String board_list(Model model) {
    //     List<Board> list = blogService.findAll(); 
    //     model.addAttribute("boards", list); 
    //     return "board_list"; 
    // }

    @GetMapping("/board_list") // 새로운 게시판 링크 지정
    public String board_list(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "") String keyword) {
        PageRequest pageable = PageRequest.of(page, 30); 
        Page<Board> list; 

        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable); 
        } else {
            list = blogService.searchByKeyword(keyword, pageable); 
        }

        model.addAttribute("boards", list); 
        model.addAttribute("totalPages", list.getTotalPages()); 
        model.addAttribute("currentPage", page); 
        model.addAttribute("keyword", keyword); 

        return "board_list"; 
    }

    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id); 

        if (list.isPresent()) {
            model.addAttribute("boards", list.get()); 
        } else {
            return "/error_page/article_error"; 
        }
        return "board_view"; 
    }

    // @GetMapping("/article_edit/{id}")
    // public String article_edit(Model model, @PathVariable Long id) {
    //     Optional<Article> list = blogService.findById(id);
    //     if (list.isPresent()) {
    //         model.addAttribute("article", list.get());
    //     } else {
    //         return "/error_page/article_error";
    //     }
    //     return "article_edit";
    // }

    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }

    @PostMapping("/api/boards") // 글쓰기 게시판 저장
    public String addboards(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/board_list"; 
    }

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

    @PostMapping("/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/board_list";
    }


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
