package com.registrartion.controller;

import com.registrartion.repository.UserRepository;
import com.registrartion.model.User;
import com.registrartion.validation.ValidationPhone;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final ValidationPhone validationPhone;

    @Autowired
    public UserController(UserRepository userRepository , ValidationPhone validationPhone) {
        this.userRepository = userRepository;
        this.validationPhone = validationPhone;
    }

    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            if (!validationPhone.isValidPhoneNumber(user.getPhoneNumber())) {
                bindingResult.rejectValue("phoneNumber", "phoneNumber.invalid", "Invalid phone number format. Expected format: +380-XXX-XXX-XXX");
                return "registration";
            }
            userRepository.save(user);
            return "redirect:/registration-success";
        }
    }

    @GetMapping("/registration-success")
    public String showRegistrationSuccessPage() {
        return "registration-success";
    }
}