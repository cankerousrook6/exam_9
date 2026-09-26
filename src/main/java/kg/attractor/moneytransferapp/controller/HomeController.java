package kg.attractor.moneytransferapp.controller;

import jakarta.validation.Valid;
import kg.attractor.moneytransferapp.dto.TopUpDto;
import kg.attractor.moneytransferapp.model.User;
import kg.attractor.moneytransferapp.service.AccountService;
import kg.attractor.moneytransferapp.service.TransactionService;
import kg.attractor.moneytransferapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TransactionService transactionService;
    private final UserService userService;
    private final AccountService accountService;
    private final MessageSource messageSource;

    @GetMapping("/")
    public String home(
            Principal principal,
            Model model
    ) {

        model.addAttribute(
                "topUp",
                new TopUpDto()
        );

        addUserData(
                principal,
                model
        );

        return "index";
    }

    @PostMapping("/top-up")
    public String topUp(
            @Valid
            @ModelAttribute("topUp")
            TopUpDto dto,
            BindingResult bindingResult,
            Principal principal,
            Model model,
            Locale locale
    ) {

        addUserData(
                principal,
                model
        );

        if (bindingResult.hasErrors()) {
            return "index";
        }

        try {

            transactionService.topUp(
                    dto.getAccountNumber(),
                    dto.getAmount()
            );

            model.addAttribute(
                    "success",
                    true
            );

            model.addAttribute(
                    "topUp",
                    new TopUpDto()
            );

        } catch (IllegalArgumentException ex) {

            model.addAttribute(
                    "error",
                    messageSource.getMessage(
                            ex.getMessage(),
                            null,
                            ex.getMessage(),
                            locale
                    )
            );
        }

        return "index";
    }

    private void addUserData(
            Principal principal,
            Model model
    ) {

        if (principal != null) {

            User user =
                    userService.getByUsername(
                            principal.getName()
                    );

            model.addAttribute(
                    "user",
                    user
            );

            model.addAttribute(
                    "accounts",
                    accountService.getUserAccounts(
                            user
                    )
            );
        }
    }
}