package kg.attractor.moneytransferapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ModelAttribute("isAdmin")
    public boolean isAdmin(
            Authentication authentication
    ) {

        if (authentication == null) {
            return false;
        }

        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(
                        authority ->
                                authority
                                        .getAuthority()
                                        .equals("ROLE_ADMIN")
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(
            IllegalArgumentException ex,
            Model model,
            Locale locale
    ) {

        log.warn(
                "Validation error: {}",
                ex.getMessage()
        );

        String message =
                messageSource.getMessage(
                        ex.getMessage(),
                        null,
                        ex.getMessage(),
                        locale
                );

        model.addAttribute(
                "errorMessage",
                message
        );

        return "error";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFoundException(
            NoSuchElementException ex,
            Model model,
            Locale locale
    ) {

        log.warn(
                "Resource not found: {}",
                ex.getMessage()
        );

        String message =
                messageSource.getMessage(
                        ex.getMessage(),
                        null,
                        ex.getMessage(),
                        locale
                );

        model.addAttribute(
                "errorMessage",
                message
        );

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(
            Exception ex,
            Model model,
            Locale locale
    ) {

        log.error(
                "Unexpected application error",
                ex
        );

        model.addAttribute(
                "errorMessage",
                messageSource.getMessage(
                        "error.general",
                        null,
                        locale
                )
        );

        return "error";
    }
}