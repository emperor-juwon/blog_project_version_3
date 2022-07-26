package site.metacoding.blog_project_version_3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.blog_project_version_3.domain.category.Category;
import site.metacoding.blog_project_version_3.domain.category.CategoryRepository;
import site.metacoding.blog_project_version_3.domain.post.Post;
import site.metacoding.blog_project_version_3.domain.post.PostRepository;
import site.metacoding.blog_project_version_3.domain.user.User;
import site.metacoding.blog_project_version_3.handler.ex.CustomException;
import site.metacoding.blog_project_version_3.util.UtilFileUpload;
import site.metacoding.blog_project_version_3.web.dto.post.PostRespDto;
import site.metacoding.blog_project_version_3.web.dto.post.PostWriteReqDto;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Value("${file.path}")
    private String uploadFolder;

    public PostRespDto 게시글목록보기(Integer userId, org.springframework.data.domain.Pageable pageable) {

        Page<Post> postsEntity = postRepository.findByUserId(userId, pageable);
        List<Category> categorysEntity = categoryRepository.findByUserId(userId);

        PostRespDto postRespDto = new PostRespDto(postsEntity, categorysEntity);
        return postRespDto;
    }

    public PostRespDto 게시글카테고리별보기(Integer userId, Integer categoryId,
            org.springframework.data.domain.Pageable pageable) {
        Page<Post> postsEntity = postRepository.findByUserIdAndCategoryId(userId, categoryId, pageable);
        List<Category> categorysEntity = categoryRepository.findByUserId(userId);

        PostRespDto postRespDto = new PostRespDto(postsEntity, categorysEntity);
        return postRespDto;

    }

    public List<Category> 게시글쓰기화면(User principal) {
        return categoryRepository.findByUserId(principal.getId());
    }

    public void 게시글쓰기(PostWriteReqDto postWriteReqDto, User principal) {

        // 1. UUID로 파일쓰고 경로 리턴 받기
        String thumnail = UtilFileUpload.write(uploadFolder, postWriteReqDto.getThumnailFile());

        // 2. 카테고리 있는지 확인
        Optional<Category> categoryOp = categoryRepository.findById(postWriteReqDto.getCategoryId());

        // 3. post DB 저장
        if (categoryOp.isPresent()) {
            Post post = postWriteReqDto.toEntity(thumnail, principal, categoryOp.get());
            postRepository.save(post);
        } else {
            throw new CustomException("해당 카테고리가 존재하지 않습니다.");
        }

    }
}
