package kg.attractor.moneytransferapp.controller;

import jakarta.validation.Valid;
import kg.attractor.moneytransferapp.dto.TransferDto;
import kg.attractor.moneytransferapp.model.Transaction;
import kg.attractor.moneytransferapp.model.User;
import kg.attractor.moneytransferapp.model.enums.TransactionStatus;
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
public class TransferController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/transfer")
    public String transferPage(
            Principal principal,
            Model model
    ) {

        User user =
                userService.getByUsername(
                        principal.getName()
                );

        model.addAttribute(
                "transfer",
                new TransferDto()
        );

        model.addAttribute(
                "accounts",
                accountService.getUserAccounts(
                        user
                )
        );

        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfer(
            @Valid
            @ModelAttribute("transfer")
            TransferDto dto,
            BindingResult bindingResult,
            Principal principal,
            Model model,
            Locale locale
    ) {

        User user =
                userService.getByUsername(
                        principal.getName()
                );

        model.addAttribute(
                "accounts",
                accountService.getUserAccounts(
                        user
                )
        );

        if (
                bindingResult.hasErrors()
        ) {

            return "transfer";
        }

        try {

            Transaction transaction =
                    transactionService.transfer(
                            user,
                            dto.getSenderAccountId(),
                            dto.getReceiverAccountNumber(),
                            dto.getAmount()
                    );

            if (
                    transaction.getStatus()
                            == TransactionStatus.PENDING
            ) {

                model.addAttribute(
                        "pending",
                        true
                );

            } else {

                model.addAttribute(
                        "success",
                        true
                );
            }

            model.addAttribute(
                    "transfer",
                    new TransferDto()
            );

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
        }

        return "transfer";
    }
}