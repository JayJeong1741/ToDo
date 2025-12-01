package kr.mjc.jiho.todo.service;

import kr.mjc.jiho.todo.Entity.User;

import kr.mjc.jiho.todo.repository.UserRepository;
import kr.mjc.jiho.todo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
                );

        return new CustomUserDetails(user);
    }
}

