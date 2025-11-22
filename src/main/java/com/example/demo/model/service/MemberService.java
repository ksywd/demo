package com.example.demo.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.domain.Member;
import com.example.demo.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

@Service
@Validated
@Transactional // 트랜잭션 처리(클래스 내 모든 메소드 대상)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 스프링 버전 5 이후, 단방향 해싱 알고리즘 지원

    // 회원 중복(이메일) 체크
    private void validateDuplicateMember(AddMemberRequest request) {
        Member findMember = memberRepository.findByEmail(request.getEmail()); // 이메일 존재 유무
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다."); // 예외처리
        }
    }

    // 회원 가입 저장
    public Member saveMember(@Valid AddMemberRequest request) {
        // 1) 이메일 중복 체크
        validateDuplicateMember(request);

        // 2) 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword); // DTO에 암호화된 비밀번호 설정

        // 3) 엔티티로 변환 후 저장
        return memberRepository.save(request.toEntity());
    }

    // 로그인 체크
    public Member loginCheck(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email); // 이메일 조회
        if (member == null) {
            throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
        }

        if (!passwordEncoder.matches(rawPassword, member.getPassword())) { // 비밀번호 확인
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member; // 인증 성공 시 회원 객체 반환
    }
}
