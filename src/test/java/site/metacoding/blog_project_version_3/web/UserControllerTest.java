package site.metacoding.blog_project_version_3.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import site.metacoding.blog_project_version_3.domain.user.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void joinForm_테스트() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/join-form"));
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));

    }

    @Test
    public void loginForm_테스트() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/login-form"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    public void passwordResetForm_테스트() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/user/password-reset-form"));
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));

    }

    @WithMockUser
    @Test
    public void updateForm_테스트() throws Exception {
        // given
        Integer id = 1;
        User principal = User.builder()
                .id(1)
                .username("juwon")
                .password("1234")
                .email("juwon@nate.com")
                .profileImg(null)
                .build();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("principal", principal);

        // when
        ResultActions resultActions = mockMvc.perform(get("/s/user" + id).session(session));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    public void profileImgUpdate_테스트() {

    }

    public void passwordReset_테스트() {

    }

    public void usernameSameCheck_테스트() {

    }

    public void join_테스트() {
    }
}
