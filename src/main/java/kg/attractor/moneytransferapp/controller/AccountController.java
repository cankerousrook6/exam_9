package kg.attractor.moneytransferapp.controller;
import kg.attractor.moneytransferapp.model.User; import kg.attractor.moneytransferapp.model.enums.CurrencyType; import kg.attractor.moneytransferapp.service.AccountService; import kg.attractor.moneytransferapp.service.UserService; import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
import java.security.Principal;
@Controller @RequiredArgsConstructor public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @GetMapping("/profile")
    public String profile(
            Principal principal,
            Model model
    ) {

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

        model.addAttribute(
                "currencies",
                CurrencyType.values()
        );

        return "profile";
    }

    @PostMapping("/accounts/create")
    public String createAccount(
            @RequestParam CurrencyType currency,
            Principal principal,
            Model model
    ) {

        User user =
                userService.getByUsername(
                        principal.getName()
                );

        try {

            accountService.createAccount(
                    user,
                    currency
            );

        } catch (
                IllegalArgumentException ex
        ) {

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

            model.addAttribute(
                    "currencies",
                    CurrencyType.values()
            );

            model.addAttribute(
                    "error",
                    ex.getMessage()
            );

            return "profile";
        }

        return "redirect:/profile";
    }
}