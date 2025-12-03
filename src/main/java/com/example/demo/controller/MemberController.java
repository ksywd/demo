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

    // 회원가입 저장
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

    // 로그인 처리 (아이디, 비밀번호 체크 + 세션 생성)
    @PostMapping("/api/login_check")
    public String checkMembers(@RequestParam String email,
                               @RequestParam String password,
                               Model model,
                               HttpServletRequest request) {

        try {
            // 1) 이메일/비밀번호 검증
            var member = memberService.loginCheck(email, password);

            // 2) 현재 요청의 세션 가져오기 (없으면 새로 생성)
            HttpSession session = request.getSession(true);

            // 3) 세션에 로그인 정보 저장
            //    → 브라우저마다 JSESSIONID가 다르기 때문에
            //      여러 사용자가 동시에 로그인 가능
            session.setAttribute("loginEmail", email);
            session.setAttribute("loginMember", member); // 필요하면 전체 객체 저장

            // 4) 뷰에 회원 정보 전달(필요 시)
            model.addAttribute("member", member);

            // 로그인 성공 후 게시판 목록으로 이동
            return "redirect:/board_list";

        } catch (IllegalArgumentException e) {
            // 로그인 실패 시 에러 메시지와 함께 로그인 페이지로
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // 로그아웃 처리 (현재 사용자 세션만 삭제)
    @GetMapping("/api/logout")
    public String member_logout(HttpServletRequest request) {

        // 현재 요청에 연결된 세션만 가져옴 (없으면 null)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 현재 사용자의 세션만 무효화
        }

        // 로그아웃 후 로그인 페이지로 이동
        return "login";
    }
}
