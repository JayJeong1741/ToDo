package kr.mjc.jiho.todo.service;
import kr.mjc.jiho.todo.Entity.User;
import kr.mjc.jiho.todo.dto.JoinRequest;
import kr.mjc.jiho.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 3단계에서 Bean 등록

    public User signup(JoinRequest req) {
        // username 중복 체크 정도는 나중에 추가 가능
        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .build();

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}

