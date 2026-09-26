package kg.attractor.moneytransferapp.controller;

import jakarta.validation.Valid;
import kg.attractor.moneytransferapp.dto.TopUpDto;
import kg.attractor.moneytransferapp.model.Transaction;
import kg.attractor.moneytransferapp.model.enums.TransactionStatus;
import kg.attractor.moneytransferapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TransactionService transactionService;

    @GetMapping("/")
    public String home(
            Model model
    ) {

        model.addAttribute(
                "topUp",
                new TopUpDto()
        );

        return "index";
    }

    @PostMapping("/top-up")
    public String topUp(
            @Valid
            @ModelAttribute("topUp")
            TopUpDto dto,
            BindingResult bindingResult,
            Model model
    ) {

        if (
                bindingResult.hasErrors()
        ) {

            return "index";
        }

        try {

            Transaction transaction =
                    transactionService.topUp(
                            dto.getAccountNumber(),
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
                    "topUp",
                    new TopUpDto()
            );

        } catch (
                IllegalArgumentException ex
        ) {

            model.addAttribute(
                    "error",
                    ex.getMessage()
            );
        }

        return "index";
    }
}