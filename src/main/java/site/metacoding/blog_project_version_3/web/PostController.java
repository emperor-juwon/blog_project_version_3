package site.metacoding.blog_project_version_3.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.metacoding.blog_project_version_3.config.auth.LoginUser;
import site.metacoding.blog_project_version_3.service.PostService;
import site.metacoding.blog_project_version_3.web.dto.post.PostRespDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/user/{id}/post")
    public String postList(@PathVariable Integer id, @AuthenticationPrincipal LoginUser loginUser, Model model) {

        PostRespDto postRespDto = postService.게시글목록보기(id);
        model.addAttribute("postRespDto", postRespDto);
        return "/post/list";
    }
}
