package site.metacoding.blog_project_version_3.web;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.blog_project_version_3.config.auth.LoginUser;
import site.metacoding.blog_project_version_3.domain.category.Category;
import site.metacoding.blog_project_version_3.domain.user.User;
import site.metacoding.blog_project_version_3.handler.ex.CustomException;
import site.metacoding.blog_project_version_3.service.PostService;
import site.metacoding.blog_project_version_3.web.dto.love.LoveRespDto;
import site.metacoding.blog_project_version_3.web.dto.post.PostDetailRespDto;
import site.metacoding.blog_project_version_3.web.dto.post.PostRespDto;
import site.metacoding.blog_project_version_3.web.dto.post.PostWriteReqDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/user/{pageOwnerId}/post")
    public String postList(Integer categoryId, @PathVariable Integer pageOwnerId,
            @AuthenticationPrincipal LoginUser loginUser,
            Model model, @PageableDefault(size = 3) Pageable pageable) {

        PostRespDto postRespDto = null;

        if (categoryId == null) {
            postRespDto = postService.게시글목록보기(pageOwnerId, pageable);
        } else {
            postRespDto = postService.게시글카테고리별보기(pageOwnerId, categoryId, pageable);
        }
        model.addAttribute("postRespDto", postRespDto);
        return "/post/list";
    }

    @PostMapping("/s/post")
    public String write(PostWriteReqDto postWriteReqDto, @AuthenticationPrincipal LoginUser loginUser) {
        postService.게시글쓰기(postWriteReqDto, loginUser.getUser());
        return "redirect:/user/" + loginUser.getUser().getId() + "/post";
    }

    @GetMapping("/s/post/write-form")
    public String wrtieForm(@AuthenticationPrincipal LoginUser loginUser, Model model) {
        List<Category> categorys = postService.게시글쓰기화면(loginUser.getUser());

        if (categorys.size() == 0) {
            throw new CustomException("카테고리 등록이 필요합니다");
        }

        model.addAttribute("categorys", categorys);
        return "/post/writeForm";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable Integer id, Model model, @AuthenticationPrincipal LoginUser loginUser) {

        PostDetailRespDto postDetailRespDto = null;

        if (loginUser == null) {
            postDetailRespDto = postService.게시글상세보기(id);
        } else {
            postDetailRespDto = postService.게시글상세보기(id, loginUser.getUser());
        }
        model.addAttribute("data", postDetailRespDto);

        return "/post/detail";
    }

    @DeleteMapping("/s/api/post/{postId}")
    public ResponseEntity<?> postDelete(@PathVariable Integer postId, @AuthenticationPrincipal LoginUser loginUser) {
        User principal = loginUser.getUser();
        postService.게시글삭제(postId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/s/api/post/{postId}/love")
    public ResponseEntity<?> love(@PathVariable Integer postId, @AuthenticationPrincipal LoginUser loginUser) {
        LoveRespDto dto = postService.좋아요(postId, loginUser.getUser());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/s/api/post/{postId}/love/{loveId}")
    public ResponseEntity<?> unLove(@PathVariable Integer loveId, @AuthenticationPrincipal LoginUser loginUser) {
        postService.좋아요취소(loveId, loginUser.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
