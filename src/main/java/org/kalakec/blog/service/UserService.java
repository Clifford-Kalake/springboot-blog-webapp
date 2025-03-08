package org.kalakec.blog.service;

import org.kalakec.blog.dto.RegistrationDto;
import org.kalakec.blog.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    User findByEmail(String email);
}
