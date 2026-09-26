package kg.attractor.moneytransferapp.controller;

import jakarta.validation.Valid;
import kg.attractor.moneytransferapp.dto.ServicePaymentDto;
import kg.attractor.moneytransferapp.model.User;
import kg.attractor.moneytransferapp.repository.ServiceProviderRepository;
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
public class ServicePaymentController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final UserService userService;

    private final ServiceProviderRepository
            serviceProviderRepository;

    private final MessageSource messageSource;

    @GetMapping("/services")
    public String services(
            Principal principal,
            Model model
    ) {

        User user =
                userService.getByUsername(
                        principal.getName()
                );

        model.addAttribute(
                "payment",
                new ServicePaymentDto()
        );

        model.addAttribute(
                "accounts",
                accountService.getUserAccounts(
                        user
                )
        );

        model.addAttribute(
                "providers",
                serviceProviderRepository.findAll()
        );

        return "services";
    }

    @PostMapping("/services")
    public String payService(
            @Valid
            @ModelAttribute("payment")
            ServicePaymentDto dto,
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

        model.addAttribute(
                "providers",
                serviceProviderRepository.findAll()
        );

        if (
                bindingResult.hasErrors()
        ) {

            return "services";
        }

        try {

            transactionService.payService(
                    user,
                    dto.getSenderAccountId(),
                    dto.getProviderId(),
                    dto.getRequisite(),
                    dto.getAmount()
            );

            model.addAttribute(
                    "success",
                    true
            );

            model.addAttribute(
                    "payment",
                    new ServicePaymentDto()
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

        return "services";
    }
}