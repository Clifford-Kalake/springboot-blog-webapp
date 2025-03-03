package org.kalakec.blog.service.Impl;

import org.kalakec.blog.dto.RegistrationDto;
import org.kalakec.blog.entity.Role;
import org.kalakec.blog.entity.User;
import org.kalakec.blog.repository.RoleRepository;
import org.kalakec.blog.repository.UserRepository;
import org.kalakec.blog.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setName(registrationDto.getFirstName() + " " + registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        //use spring security to encrypt the password
        user.setPassword(registrationDto.getPassword());
        Role role = roleRepository.findByName("ROLE_GUEST");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }
}
