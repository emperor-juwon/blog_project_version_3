package site.metacoding.blog_project_version_3.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.blog_project_version_3.handler.ex.CustomException;
import site.metacoding.blog_project_version_3.service.UserService;
import site.metacoding.blog_project_version_3.web.dto.user.JoinReqDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login-form")
    public String loginForm() {
        return "/user/loginForm";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new CustomException(errorMap.toString());
        }
        userService.회원가입(joinReqDto.toEntity());

        return "redirect:/login-form";
    }

    @GetMapping("/api/user/username-same-check")
    public ResponseEntity<?> usernameSameCheck(String username) {
        boolean isNotSame = userService.유저네임중복체크(username);
        return new ResponseEntity<>(isNotSame, HttpStatus.OK);
    }
}
