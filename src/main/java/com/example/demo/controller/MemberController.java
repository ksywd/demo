package com.example.demo.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join_new") // 회원 가입 페이지 연결
    public String join_new() {
        return "join_new";
    }

    @PostMapping("/api/members") // 회원 가입 저장
    public String addmembers(@Valid @ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
        return "join_end";
    }

    @GetMapping("/login") // 로그인 페이지 연결
    public String member_login() {
        return "login";
    }

    @PostMapping("/api/login_check") // 로그인(아이디, 비밀번호) 체크
    public String checkMembers(@RequestParam String email,
                               @RequestParam String password,
                               Model model,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        try {
            // 🔻 기존 세션이 있으면 초기화 (단일 사용자 로그인 처리)
            HttpSession session = request.getSession(false); // 기존 세션 가져오기 (없으면 null)
            if (session != null) {
                session.invalidate();                         // 서버 세션 무효화

                // JSESSIONID 쿠키 삭제 (클라이언트 쪽 세션 쿠키 초기화)
                Cookie cookie = new Cookie("JSESSIONID", null);
                cookie.setPath("/");                          // 쿠키 경로
                cookie.setMaxAge(0);                          // 0초 → 즉시 삭제
                response.addCookie(cookie);                   // 응답으로 쿠키 전송
            }

            // 🔻 새로운 세션 생성
            HttpSession newSession = request.getSession(true);

            // 로그인 검증
            var member = memberService.loginCheck(email, password);

            // 세션 ID 생성
            String sessionId = UUID.randomUUID().toString();

            // 세션에 값 저장 (PDF 내용 반영)
            newSession.setAttribute("userId", sessionId); // 임의의 고유 ID
            newSession.setAttribute("email", email);      // 로그인한 사용자 이메일

            // 뷰로 회원 정보 전달
            model.addAttribute("member", member);

            return "redirect:/board_list";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // 🔻 로그아웃 컨트롤러 (PDF: @GetMapping("/api/logout"))
    @GetMapping("/api/logout") // 로그아웃 버튼 동작
    public String member_logout(Model model,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        try {
            // 기존 세션 가져오기 (없으면 null)
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // 기존 세션 무효화

                // JSESSIONID 쿠키 초기화(삭제)
                Cookie cookie = new Cookie("JSESSIONID", null); // 기본 이름은 JSESSIONID
                cookie.setPath("/");        // 쿠키 경로
                cookie.setMaxAge(0);        // 0초 → 삭제
                response.addCookie(cookie); // 응답에 쿠키 설정
            }

            // 로그아웃 후 새 세션 생성 (옵션)
            session = request.getSession(true);
            System.out.println("세션 userId: " + session.getAttribute("userId")); // 초기화 후 IDE 터미널에 세션 값 출력(보통 null)

            // 로그아웃 후 로그인 페이지로 이동
            return "login";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login"; // 예외 발생 시에도 로그인 페이지로
        }
    }
}
