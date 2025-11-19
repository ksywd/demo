package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.domain.Member;
import com.example.demo.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;


    @Service
    @Transactional // 트랜잭션 처리(클래스 내 모든 메소드 대상)
    @RequiredArgsConstructor
    public class MemberService {
    @Autowired
        private final MemberRepository memberRepository;
        private final PasswordEncoder passwordEncoder; // 스프링 버전 5 이후, 단방향 해싱 알고리즘 지원

        private void validateDuplicateMember(AddMemberRequest request) {
            Member findMember = memberRepository.findByEmail(request.toEntity().getEmail()); // 이메일 존재 유무

            if (findMember != null) {
                throw new IllegalStateException("이미 가입된 회원입니다."); // 예외처리
            }
        }

        public Member saveMember(AddMemberRequest request) {
            validateDuplicateMember(request); // 이메일 체크

            Member member = request.toEntity();
            String encodedPassword = passwordEncoder.encode(member.getPassword());
            try {
                java.lang.reflect.Field pwField = Member.class.getDeclaredField("password");
                pwField.setAccessible(true);
                pwField.set(member, encodedPassword);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalStateException("Failed to set encoded password on Member", e);
            }

            return memberRepository.save(member);
        }
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

