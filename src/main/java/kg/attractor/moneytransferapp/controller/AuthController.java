package kg.attractor.moneytransferapp.controller;

import jakarta.validation.Valid;
import kg.attractor.moneytransferapp.dto.UserRegisterDto;
import kg.attractor.moneytransferapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerPage(
            Model model
    ) {

        model.addAttribute(
                "user",
                new UserRegisterDto()
        );

        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid
            @ModelAttribute("user")
            UserRegisterDto dto,
            BindingResult bindingResult
    ) {

        if (
                userService.existsByUsername(
                        dto.getUsername()
                )
        ) {

            bindingResult.rejectValue(
                    "username",
                    "register.username.exists"
            );
        }

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        userService.register(dto);

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}