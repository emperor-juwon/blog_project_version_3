package site.metacoding.blog_project_version_3.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import site.metacoding.blog_project_version_3.domain.user.User;
import site.metacoding.blog_project_version_3.domain.user.UserRepository;
import site.metacoding.blog_project_version_3.domain.visit.Visit;
import site.metacoding.blog_project_version_3.domain.visit.VisitRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VisitRepository visitRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncorder = new BCryptPasswordEncoder();

    @InjectMocks
    private UserService userService;

    @Test
    public void 회원가입_테스트() {
        // given1
        User givenUser = User.builder()
                .username("juwon")
                .password("1234")
                .email("juwon@ntae.com")
                .build();

        // stub1
        User mockUserEntity = User.builder()
                .id(1)
                .username("juwon")
                .password("1234")
                .email("juwon@ntae.com")
                .profileImg(null)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        when(userRepository.save(givenUser)).thenReturn(mockUserEntity);

        // given2
        Visit givenVisit = Visit.builder()
                .totalCount(0L)
                .user(mockUserEntity)
                .build();

        // stub2
        Visit mockVisitEntity = Visit.builder()
                .id(1)
                .totalCount(0L)
                .user(mockUserEntity)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        when(visitRepository.save(givenVisit)).thenReturn(mockVisitEntity);

        // when
        User userEntity = userService.회원가입(givenUser);
        // then
        assertEquals(givenUser.getEmail(), userEntity.getEmail());
    }

    public void 프로파일이미지변경_테스트() {

    }

    public void 패스워드초기화_테스트() {

    }
}
