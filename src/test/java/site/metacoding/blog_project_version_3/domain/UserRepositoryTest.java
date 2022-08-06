package site.metacoding.blog_project_version_3.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Test
    @Order(2)
    public void findByUsername_테스트() {
        // given
        String username = "juwon";

        // when
        Optional<User> userOp = userRepository.findByUsername(username);

        // then
        if (userOp.isPresent()) {
            User user = userOp.get();

            assertEquals(username, user.getUsername());
        }
    }

    @Test
    @Order(3)
    public void findById_테스트() {

        // given
        Integer id = 1;

        // when
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isPresent()) {
            User userEntity = userOp.get();

            assertEquals(id, userEntity.getId());
        }
    }

    @Test
    @Order(4)
    public void findByUsernameAndEmail_테스트() {

        // given
        String username = "juwon";
        String email = "juwon@nate.com";

        // when
        Optional<User> userOp = userRepository.findByUsernameAndEmail(username, email);

        // then
        if (userOp.isPresent()) {
            User userEntity = userOp.get();

            assertEquals(username, userEntity.getUsername());
            assertEquals(email, userEntity.getEmail());
        }

    }
}
