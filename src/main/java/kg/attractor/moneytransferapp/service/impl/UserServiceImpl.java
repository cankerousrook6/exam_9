package kg.attractor.moneytransferapp.service.impl;

import kg.attractor.moneytransferapp.dto.UserRegisterDto;
import kg.attractor.moneytransferapp.model.User;
import kg.attractor.moneytransferapp.model.enums.UserRole;
import kg.attractor.moneytransferapp.repository.UserRepository;
import kg.attractor.moneytransferapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(
            UserRegisterDto dto
    ) {

        if (
                userRepository.existsByUsername(
                        dto.getUsername()
                )
        ) {

            log.warn(
                    "Username {} already exists",
                    dto.getUsername()
            );

            throw new IllegalArgumentException(
                    "register.username.exists"
            );
        }

        User user =
                User.builder()
                        .username(
                                dto.getUsername()
                        )
                        .password(
                                passwordEncoder.encode(
                                        dto.getPassword()
                                )
                        )
                        .role(
                                UserRole.USER
                        )
                        .build();

        userRepository.save(
                user
        );

        log.info(
                "User {} registered",
                user.getUsername()
        );
    }

    @Override
    public User getByUsername(
            String username
    ) {

        return userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> {

                            log.warn(
                                    "User {} not found",
                                    username
                            );

                            return new UsernameNotFoundException(
                                    "user.notFound"
                            );
                        }
                );
    }

    @Override
    public boolean existsByUsername(
            String username
    ) {

        return userRepository
                .existsByUsername(
                        username
                );
    }
}