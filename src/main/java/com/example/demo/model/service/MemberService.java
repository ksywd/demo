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
@Transactional   // 서비스 계층에서 트랜잭션 관리
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 이메일 중복 여부 확인
    private void validateDuplicateMember(AddMemberRequest request) {
        Member findMember = memberRepository.findByEmail(request.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    // 회원 가입 처리
    public Member saveMember(@Valid AddMemberRequest request) {
        validateDuplicateMember(request);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);   // 암호화하여 저장

        return memberRepository.save(request.toEntity());
    }

    // 로그인 검증
    public Member loginCheck(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
        }
        
        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }

}