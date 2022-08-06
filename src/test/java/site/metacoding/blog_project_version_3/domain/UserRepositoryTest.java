package site.metacoding.blog_project_version_3.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import site.metacoding.blog_project_version_3.domain.user.User;
import site.metacoding.blog_project_version_3.domain.user.UserRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void save_테스트() {
        String username = "juwon";
        String password = "1234";
        String email = "juwon@nate.com";
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime updateDate = LocalDateTime.now();
        User user = new User(null, username, password, email, null, createDate, updateDate);

        // when
        User userEntity = userRepository.save(user);
        // then
        assertEquals(username, userEntity.getUsername());
    }

    public void findByUsername_테스트() {

    }

    public void findById_테스트() {

    }

    public void findByUsernameAndEmail_테스트() {

    }
}
