package de.makerhub.application;

import de.makerhub.persistence.Account;
import de.makerhub.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public Account getCurrentUser() {
        return userRepository.findById(UUID.randomUUID()).orElseThrow();
    }
}
