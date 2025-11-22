package com.example.demo.model.service;

import com.example.demo.model.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddMemberRequest {

    @NotBlank(message = "이름을 입력하세요.")
    @Pattern(regexp = "^[A-Za-z가-힣0-9]+$", message = "이름에는 공백이나 특수문자를 사용할 수 없습니다.")
    private String name;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$",
             message = "비밀번호는 대문자와 소문자를 모두 포함해야 합니다.")
    private String password;

    @NotBlank(message = "나이를 입력하세요.")
    @Pattern(regexp = "^(1[9]|[2-8][0-9]|90)$",
             message = "나이는 19세 이상 90세 이하만 가능합니다.")
    private String age;

    private String mobile;

    private String address;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .age(age)
                .mobile(mobile)
                .address(address)
                .build();
    }
}
