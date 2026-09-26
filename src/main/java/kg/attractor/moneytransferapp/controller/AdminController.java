package kg.attractor.moneytransferapp.controller;

import kg.attractor.moneytransferapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final TransactionService transactionService;
    private final MessageSource messageSource;

    @GetMapping("/transactions")
    public String transactions(
            Model model
    ) {

        model.addAttribute(
                "transactions",
                transactionService.getAll()
        );

        return "admin/transactions";
    }

    @GetMapping("/transactions/pending")
    public String pendingTransactions(
            Model model
    ) {

        model.addAttribute(
                "transactions",
                transactionService.getPending()
        );

        return "admin/transactions";
    }

    @GetMapping("/transactions/{id}")
    public String transactionDetails(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "transaction",
                transactionService.getById(id)
        );

        return "admin/transaction-details";
    }

    @PostMapping("/transactions/{id}/approve")
    public String approve(
            @PathVariable Long id,
            Model model,
            Locale locale
    ) {

        try {

            transactionService.approve(id);

            return "redirect:/admin/transactions/"
                    + id
                    + "?approved";

        } catch (
                IllegalArgumentException ex
        ) {

            model.addAttribute(
                    "transaction",
                    transactionService.getById(id)
            );

            model.addAttribute(
                    "error",
                    messageSource.getMessage(
                            ex.getMessage(),
                            null,
                            ex.getMessage(),
                            locale
                    )
            );

            return "admin/transaction-details";
        }
    }
}