package kr.mjc.jiho.todo.controller;

import kr.mjc.jiho.todo.service.UserService;
import kr.mjc.jiho.todo.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // templates/login.html
    }

    // 회원가입 페이지
    @GetMapping("/join")
    public String joinForm(JoinRequest joinRequest) {
        return "join"; // templates/join.html
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String join(JoinRequest joinRequest) {
        userService.signup(joinRequest);
        // 가입 후 로그인 페이지로 이동
        return "redirect:/login";
    }
}
