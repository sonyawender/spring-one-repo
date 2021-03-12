package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.RoleRepository;
import ru.geekbrains.service.UserDTO;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private final RoleRepository roleRepository;

    private final UserService userService;

    @Autowired
    public RegisterController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        Role role = roleRepository.findByName("ROLE_GUEST");
        model.addAttribute("roles", role);
        model.addAttribute("userForm", new UserDTO(role));
        return "registration_form";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid UserDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration_form";
        }
        userService.save(user);
        return "redirect:/login";
    }
}
