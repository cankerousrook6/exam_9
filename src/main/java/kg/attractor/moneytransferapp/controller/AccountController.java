package kg.attractor.moneytransferapp.controller;

import kg.attractor.moneytransferapp.model.User;
import kg.attractor.moneytransferapp.model.enums.CurrencyType;
import kg.attractor.moneytransferapp.service.AccountService;
import kg.attractor.moneytransferapp.service.TransactionService;
import kg.attractor.moneytransferapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;
    private final MessageSource messageSource;

    @GetMapping("/profile")
    public String profile(
            Principal principal,
            Model model
    ) {

        User user =
                userService.getByUsername(
                        principal.getName()
                );

        fillProfile(
                user,
                model
        );

        return "profile";
    }

    @PostMapping("/accounts/create")
    public String createAccount(
            @RequestParam
            CurrencyType currency,
            Principal principal,
            Model model,
            Locale locale
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

            return "redirect:/profile";

        } catch (
                IllegalArgumentException ex
        ) {

            model.addAttribute(
                    "error",
                    messageSource.getMessage(
                            ex.getMessage(),
                            null,
                            ex.getMessage(),
                            locale
                    )
            );

            fillProfile(
                    user,
                    model
            );

            return "profile";
        }
    }

    private void fillProfile(
            User user,
            Model model
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
                "transactions",
                transactionService
                        .getUserTransactions(
                                user,
                                null,
                                null,
                                "date"
                        )
        );
    }
}