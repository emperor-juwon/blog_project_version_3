package site.metacoding.blog_project_version_3.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import site.metacoding.blog_project_version_3.config.auth.LoginUser;

@Controller
public class MainController {

    @GetMapping({ "/" })
    public String main(@AuthenticationPrincipal LoginUser loginUser) {
        return "main";
    }
}
