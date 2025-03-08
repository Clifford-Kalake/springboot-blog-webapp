package org.kalakec.blog.controller;

import jakarta.validation.Valid;
import org.kalakec.blog.dto.RegistrationDto;
import org.kalakec.blog.entity.User;
import org.kalakec.blog.repository.UserRepository;
import org.kalakec.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    private UserService userService;

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //handler method to handle login page request
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    //handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        //this object holds form data
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    //handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult result, Model model){
        User existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", "null", "This email is already in use");
        }
        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }
}
