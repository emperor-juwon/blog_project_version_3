package site.metacoding.blog_project_version_3.config.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.blog_project_version_3.domain.user.User;
import site.metacoding.blog_project_version_3.domain.user.UserRepository;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(1);
        Optional<User> userOp = userRepository.findByUsername(username);
        System.out.println(2);
        if (userOp.isPresent()) {
            return new LoginUser(userOp.get());
        }
        System.out.println(3);
        return null;
    }

}
