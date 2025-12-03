package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지
    @GetMapping("/join_new")
    public String join_new() {
        return "join_new";
    }

    // 회원가입 처리
    @PostMapping("/api/members")
    public String addmembers(@Valid @ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
        return "join_end";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String member_login() {
        return "login";
    }

    // 로그인 검증 및 세션 생성
    @PostMapping("/api/login_check")
    public String checkMembers(@RequestParam String email,
                               @RequestParam String password,
                               Model model,
                               HttpServletRequest request) {

        try {
            var member = memberService.loginCheck(email, password);

            // 세션 생성 후 로그인 정보 저장
            HttpSession session = request.getSession(true);
            session.setAttribute("loginEmail", email);
            session.setAttribute("loginMember", member);

            model.addAttribute("member", member);

            return "redirect:/board_list";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // 로그아웃: 현재 세션만 종료
    @GetMapping("/api/logout")
    public String member_logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "login";
    }
}
