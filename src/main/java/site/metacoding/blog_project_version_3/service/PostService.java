package site.metacoding.blog_project_version_3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.blog_project_version_3.domain.category.Category;
import site.metacoding.blog_project_version_3.domain.category.CategoryRepository;
import site.metacoding.blog_project_version_3.domain.post.Post;
import site.metacoding.blog_project_version_3.domain.post.PostRepository;
import site.metacoding.blog_project_version_3.domain.user.User;
import site.metacoding.blog_project_version_3.domain.user.UserRepository;
import site.metacoding.blog_project_version_3.domain.visit.Visit;
import site.metacoding.blog_project_version_3.domain.visit.VisitRepository;
import site.metacoding.blog_project_version_3.handler.ex.CustomApiException;
import site.metacoding.blog_project_version_3.handler.ex.CustomException;
import site.metacoding.blog_project_version_3.util.UtilFileUpload;
import site.metacoding.blog_project_version_3.util.UtilPost;
import site.metacoding.blog_project_version_3.web.dto.post.PostDetailRespDto;
import site.metacoding.blog_project_version_3.web.dto.post.PostRespDto;
import site.metacoding.blog_project_version_3.web.dto.post.PostWriteReqDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final VisitRepository visitRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public PostRespDto 게시글목록보기(Integer pageOwnerId, Pageable pageable) {

        Page<Post> postsEntity = postRepository.findByUserId(pageOwnerId, pageable);
        List<Category> categorysEntity = categoryRepository.findByUserId(pageOwnerId);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < postsEntity.getTotalPages(); i++) {
            pageNumbers.add(i);
        }

        Visit visitEntity = visitIncrease(pageOwnerId);

        PostRespDto postRespDto = new PostRespDto(postsEntity, categorysEntity, pageOwnerId,
                postsEntity.getNumber() - 1,
                postsEntity.getNumber() + 1, pageNumbers, visitEntity.getTotalCount());

        return postRespDto;
    }

    public PostRespDto 게시글카테고리별보기(Integer pageOwnerId, Integer categoryId,
            org.springframework.data.domain.Pageable pageable) {
        Page<Post> postsEntity = postRepository.findByUserIdAndCategoryId(pageOwnerId, categoryId, pageable);
        List<Category> categorysEntity = categoryRepository.findByUserId(pageOwnerId);
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < postsEntity.getTotalPages(); i++) {
            pageNumbers.add(i);
        }

        Visit visitEntity = visitIncrease(pageOwnerId);

        PostRespDto postRespDto = new PostRespDto(postsEntity, categorysEntity, pageOwnerId,
                postsEntity.getNumber() - 1,
                postsEntity.getNumber() + 1, pageNumbers, visitEntity.getTotalCount());
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

    @Transactional
    public PostDetailRespDto 게시글상세보기(Integer id) {
        PostDetailRespDto postDetailRespDto = new PostDetailRespDto();

        Post postEntity = basicFindById(id);

        visitIncrease(postEntity.getUser().getId());

        postDetailRespDto.setPost(postEntity);
        postDetailRespDto.setPageOwner(false);

        return postDetailRespDto;

    }

    @Transactional
    public void 게시글삭제(Integer id, User principal) {

        Post postEntity = basicFindById(id);

        if (authCheck(postEntity.getUser().getId(), principal.getId())) {
            postRepository.deleteById(id);
        } else {
            throw new CustomApiException("삭제 권한이 없습니다");
        }
    }

    @Transactional
    public PostDetailRespDto 게시글상세보기(Integer id, User principal) {
        PostDetailRespDto postDetailRespDto = new PostDetailRespDto();

        Post postEntity = basicFindById(id);

        boolean isAuth = authCheck(postEntity.getUser().getId(), principal.getId());

        visitIncrease(postEntity.getUser().getId());

        postDetailRespDto.setPost(postEntity);
        postDetailRespDto.setPageOwner(isAuth);

        return postDetailRespDto;
    }

    private Post basicFindById(Integer postId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if (postOp.isPresent()) {
            Post postEntity = postOp.get();
            return postEntity;
        } else {
            throw new CustomApiException("해당 게시글이 존재하지 않습니다");
        }
    }

    private boolean authCheck(Integer principalId, Integer pageOwnerId) {
        boolean isAuth = false;
        if (principalId == pageOwnerId) {
            isAuth = true;
        } else {
            isAuth = false;
        }
        return isAuth;
    }

    private Visit visitIncrease(Integer pageOwnerId) {
        Optional<Visit> visitOp = visitRepository.findById(pageOwnerId);
        if (visitOp.isPresent()) {
            Visit visitEntity = visitOp.get();
            Long totalCount = visitEntity.getTotalCount();
            visitEntity.setTotalCount(totalCount + 1);
            return visitEntity;
        } else {
            log.error("심각!!", "회원가입 시 visit이 안 만들어지는 심각한 오류가 생겼습니다");
            throw new CustomException("일시적 문제 생김, 관리자에게 문의바람");
        }
    }
}
