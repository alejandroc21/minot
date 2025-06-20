package com.alejandroct.minot_api.user.service;

import com.alejandroct.minot_api.user.model.User;

public interface IUserService {
    User findUserByEmail(String email);

    boolean userExists(String email);

    User save(User user);
}
