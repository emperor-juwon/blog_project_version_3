package site.metacoding.blog_project_version_3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samskivert.mustache.Mustache.Visitor;

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
    private final UserRepository userRepository;

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

        PostRespDto postRespDto = new PostRespDto(postsEntity, categorysEntity, pageOwnerId,
                postsEntity.getNumber() - 1,
                postsEntity.getNumber() + 1, pageNumbers, 0L);

        Optional<User> pageOwnerOp = userRepository.findById(pageOwnerId);

        if (pageOwnerOp.isPresent()) {
            User pageOwnerEntity = pageOwnerOp.get();
            Optional<Visit> visitOp = visitRepository.findById(pageOwnerEntity.getId());
            if (visitOp.isPresent()) {
                Visit visitsEntity = visitOp.get();

                // DTO에 방문자 수 담기
                postRespDto.setTotalCount(visitsEntity.getTotalCount());
                Long totalCount = visitsEntity.getTotalCount();
                visitsEntity.setTotalCount(totalCount + 1);
            } else {
                log.error("심각!!!", "회원가입 시 visit이 안만들어지는 심각한 오류 발생");
                throw new CustomException("일시적 문제가 생겼습니다. 관리자에게 문의바랍니다.");
            }
        } else {
            throw new CustomException("해당 블로그는 없는 페이지입니다.");
        }
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
        PostRespDto postRespDto = new PostRespDto(postsEntity, categorysEntity, pageOwnerId,
                postsEntity.getNumber() - 1,
                postsEntity.getNumber() + 1, pageNumbers, 0L);

        Optional<User> pageOwnerOp = userRepository.findById(pageOwnerId);

        if (pageOwnerOp.isPresent()) {
            User pageOwnerEntity = pageOwnerOp.get();
            Optional<Visit> visitOp = visitRepository.findById(pageOwnerEntity.getId());
            if (visitOp.isPresent()) {
                Visit visitEntity = visitOp.get();
                postRespDto.setTotalCount(visitEntity.getTotalCount());

                Long totalCount = visitEntity.getTotalCount();
                visitEntity.setTotalCount(totalCount + 1);
            } else {
                log.error("미친 심각!!", "회원가입 시 visit이 안 만들어지는 심각한 오류 발생");
                throw new CustomException("일시적 문제가 생겼습니다. 관리자에게 문의해주세요");
            }
        } else {
            throw new CustomException("해당 블로그는 없는 페이지 입니다");
        }
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

        Optional<Post> postOp = postRepository.findById(id);
        if (postOp.isPresent()) {
            Post postEntity = postOp.get();
            postDetailRespDto.setPost(postEntity);
            postDetailRespDto.setPageOwner(false);

            // 방문자 카운트 증가
            Optional<Visit> visitOp = visitRepository.findById(postEntity.getUser().getId());
            if (visitOp.isPresent()) {
                Visit visitEntity = visitOp.get();
                Long totalCount = visitEntity.getTotalCount();
                visitEntity.setTotalCount(totalCount + 1);
            } else {
                log.error("심각!!!", "회원가입 시 visit이 안 만들어지는 오류 발생");
                throw new CustomException("일시적 문제가 생겼습니다. 관리자에게 문의 바랍니다.");
            }
            return postDetailRespDto;
        } else {
            throw new CustomException("해당 게시글을 찾을 수 없습니다");
        }
    }

    @Transactional
    public void 게시글삭제(Integer id, User principal) {
        Optional<Post> postOp = postRepository.findById(id);

        if (postOp.isPresent()) {
            Post postEntity = postOp.get();

            if (principal.getId() == postEntity.getUser().getId()) {
                postRepository.deleteById(id);
            } else {
                throw new CustomApiException("삭제 권한이 없습니다");
            }
        } else {
            throw new CustomApiException("해당 게시글이 존재하지 않습니다");
        }
    }

    @Transactional
    public PostDetailRespDto 게시글상세보기(Integer id, User principal) {
        PostDetailRespDto postDetailRespDto = new PostDetailRespDto();

        Integer postId = id;
        Integer pageOwnerId = null;
        Integer loginUserId = principal.getId();

        Optional<Post> postOp = postRepository.findById(id);

        if (postOp.isPresent()) {
            Post postEntity = postOp.get();
            postDetailRespDto.setPost(postEntity);

            pageOwnerId = postEntity.getUser().getId();

            // 두 값을 비교해서 동일하면 isPageOwner에 true를 추가해준다.
            if (pageOwnerId == loginUserId) {
                postDetailRespDto.setPageOwner(true);
            } else {
                postDetailRespDto.setPageOwner(false);
            }

            // 방문자 카운터 증가
            Optional<Visit> visitOp = visitRepository.findById(postEntity.getUser().getId());
            if (visitOp.isPresent()) {
                Visit visitEntity = visitOp.get();
                Long totalCount = visitEntity.getTotalCount();
                visitEntity.setTotalCount(totalCount + 1);
            } else {
                log.error("미친 심각", "회원가입할때 Visit이 안 만들어지는 심각한 오류가 있습니다.");
                throw new CustomException("일시적 문제가 생겼습니다. 관리자에게 문의해주세요.");
            }
            return postDetailRespDto;
        } else {
            throw new CustomException("해당 게시글을 찾을 수 없습니다");
        }
    }
}
