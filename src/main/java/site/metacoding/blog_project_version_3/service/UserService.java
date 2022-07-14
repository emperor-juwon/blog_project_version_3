package site.metacoding.blog_project_version_3.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.blog_project_version_3.domain.user.User;
import site.metacoding.blog_project_version_3.domain.user.UserRepository;
import site.metacoding.blog_project_version_3.handler.ex.CustomException;
import site.metacoding.blog_project_version_3.util.email.EmailUtil;
import site.metacoding.blog_project_version_3.web.dto.user.PasswordResetReqDto;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailUtil emailUtil;

    @Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);
    }

    public boolean 유저네임중복체크(String username) {
        Optional<User> userOp = userRepository.findByUsername(username);

        if (userOp.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void 패스워드초기화(PasswordResetReqDto passwordResetReqDto) {
        Optional<User> userOp = userRepository.findByUsernameAndEmail(
                passwordResetReqDto.getUsername(),
                passwordResetReqDto.getEmail());

        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            String encPassword = bCryptPasswordEncoder.encode("9999");
            userEntity.setPassword(encPassword);
        } else {
            throw new CustomException("해당 이메일이 존재하지 않습니다");
        }
        emailUtil.sendEmail("won5354@gmail.com", "비밀번호초기화", "초기화된 비밀번호: 9999");
    }

}
