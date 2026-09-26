package kg.attractor.moneytransferapp.controller;

import kg.attractor.moneytransferapp.model.User;
import kg.attractor.moneytransferapp.service.TransactionService;
import kg.attractor.moneytransferapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping("/transactions")
    public String transactions(
            Principal principal,
            @RequestParam(
                    required = false
            )
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE
            )
            LocalDate dateFrom,
            @RequestParam(
                    required = false
            )
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE
            )
            LocalDate dateTo,
            @RequestParam(
                    defaultValue = "date"
            )
            String sort,
            Model model
    ) {

        User user =
                userService.getByUsername(
                        principal.getName()
                );

        model.addAttribute(
                "transactions",
                transactionService
                        .getUserTransactions(
                                user,
                                dateFrom,
                                dateTo,
                                sort
                        )
        );

        model.addAttribute(
                "userId",
                user.getId()
        );

        model.addAttribute(
                "dateFrom",
                dateFrom
        );

        model.addAttribute(
                "dateTo",
                dateTo
        );

        model.addAttribute(
                "sort",
                sort
        );

        return "transactions";
    }
}