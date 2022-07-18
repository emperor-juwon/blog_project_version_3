package site.metacoding.blog_project_version_3.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.blog_project_version_3.util.Script;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    @GetMapping("/s/category/write-form")
    public String writeForm() {
        return "/category/writeForm";
    }

    @PostMapping("/s/category")
    public @ResponseBody String write() {
        return Script.href("/s/category/write-form", "카테고리등록완료");
    }
}
