package site.metacoding.blog_project_version_3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.blog_project_version_3.domain.category.Category;
import site.metacoding.blog_project_version_3.domain.category.CategoryRepository;
import site.metacoding.blog_project_version_3.domain.post.Post;
import site.metacoding.blog_project_version_3.domain.post.PostRepository;
import site.metacoding.blog_project_version_3.web.dto.post.PostRespDto;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostRespDto 게시글목록보기(int userId) {
        List<Post> postsEntity = postRepository.findByUserId(userId);
        List<Category> categorysEntity = categoryRepository.findByUserId(userId);

        PostRespDto postRespDto = new PostRespDto(postsEntity, categorysEntity);
        return postRespDto;
    }
}
