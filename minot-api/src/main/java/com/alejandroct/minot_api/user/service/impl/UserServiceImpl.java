package com.alejandroct.minot_api.user.service.impl;

import static com.alejandroct.minot_api.constants.ErrorMessageConstants.*;
import com.alejandroct.minot_api.user.model.User;
import com.alejandroct.minot_api.user.repository.UserRepository;
import com.alejandroct.minot_api.user.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public boolean userExists(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }
}
