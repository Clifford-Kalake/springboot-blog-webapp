package org.kalakec.blog.service;

import org.kalakec.blog.dto.RegistrationDto;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
}
