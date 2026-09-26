package kg.attractor.moneytransferapp.service.impl;

import kg.attractor.moneytransferapp.model.Account;
import kg.attractor.moneytransferapp.model.User;
import kg.attractor.moneytransferapp.model.enums.CurrencyType;
import kg.attractor.moneytransferapp.repository.AccountRepository;
import kg.attractor.moneytransferapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl
        implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public void createAccount(
            User user,
            CurrencyType currency
    ) {

        if (
                accountRepository.countByUser(
                        user
                ) >= 3
        ) {

            log.warn(
                    "User {} reached account limit",
                    user.getUsername()
            );

            throw new IllegalArgumentException(
                    "account.limit"
            );
        }

        if (
                accountRepository
                        .existsByUserAndCurrency(
                                user,
                                currency
                        )
        ) {

            throw new IllegalArgumentException(
                    "account.currencyExists"
            );
        }

        Account account =
                Account.builder()
                        .accountNumber(
                                generateAccountNumber()
                        )
                        .currency(currency)
                        .balance(
                                BigDecimal.ZERO
                        )
                        .user(user)
                        .build();

        accountRepository.save(
                account
        );

        log.info(
                "Account {} created for user {}",
                account.getAccountNumber(),
                user.getUsername()
        );
    }

    @Override
    public List<Account> getUserAccounts(
            User user
    ) {

        return accountRepository
                .findAllByUser(user);
    }

    @Override
    public Account getById(
            Long id
    ) {

        return accountRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new NoSuchElementException(
                                        "account.notFound"
                                )
                );
    }

    @Override
    public Account getByIdAndUser(
            Long id,
            User user
    ) {

        Account account =
                getById(id);

        if (
                !account.getUser()
                        .getId()
                        .equals(
                                user.getId()
                        )
        ) {

            throw new IllegalArgumentException(
                    "account.accessDenied"
            );
        }

        return account;
    }

    private String generateAccountNumber() {

        Random random =
                new Random();

        String number;

        do {

            number =
                    String.valueOf(
                            100000
                                    + random.nextInt(
                                    900000
                            )
                    );

        } while (
                accountRepository
                        .existsByAccountNumber(
                                number
                        )
        );

        return number;
    }
}