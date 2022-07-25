package site.metacoding.blog_project_version_3.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.blog_project_version_3.config.auth.LoginUser;
import site.metacoding.blog_project_version_3.domain.category.Category;
import site.metacoding.blog_project_version_3.domain.user.User;
import site.metacoding.blog_project_version_3.service.CategoryService;
import site.metacoding.blog_project_version_3.util.Script;
import site.metacoding.blog_project_version_3.util.UtilValid;
import site.metacoding.blog_project_version_3.web.dto.category.CategoryWriteReqDto;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final HttpSession session;

    @GetMapping("/s/category/write-form")
    public String writeForm() {
        return "/category/writeForm";
    }

    @PostMapping("/s/category")
    public @ResponseBody String write(
            @Valid CategoryWriteReqDto categoryWriteReqDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginUser loginUser) {

        UtilValid.요청에러처리(bindingResult);

        User principal = loginUser.getUser();
        Category category = categoryWriteReqDto.toEntity(principal);
        categoryService.카테고리등록(category);
        return Script.href("/s/category/write-form", "카테고리등록완료");
    }
}
