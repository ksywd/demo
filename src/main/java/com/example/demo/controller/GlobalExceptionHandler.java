package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.beans.TypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // URL 경로 변수 타입이 맞지 않을 때 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleMethodArgTypeMismatch(MethodArgumentTypeMismatchException ex, Model model) {
        model.addAttribute("errorMessage", "잘못된 게시판 접근입니다! (" + ex.getValue() + ")");
        return "error_page";
    }

    // 요청 파라미터 타입 오류 처리
    @ExceptionHandler(TypeMismatchException.class)
    public String handleTypeMismatch(TypeMismatchException ex, Model model) {
        model.addAttribute("errorMessage", "잘못된 요청입니다.");
        return "error_page";
    }
}
