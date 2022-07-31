package site.metacoding.blog_project_version_3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.blog_project_version_3.domain.post.Post;
import site.metacoding.blog_project_version_3.domain.post.PostRepository;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final PostRepository postRepository;

    @GetMapping({ "/" })
    public String main(Model model) {
        List<Post> postsEntity = postRepository.mFindByPopular();
        model.addAttribute("posts", postsEntity);
        return "main";
    }
}
