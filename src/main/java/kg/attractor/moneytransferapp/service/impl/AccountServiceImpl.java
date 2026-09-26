package kg.attractor.moneytransferapp.service.impl;
import kg.attractor.moneytransferapp.model.Account; import kg.attractor.moneytransferapp.model.User; import kg.attractor.moneytransferapp.model.enums.CurrencyType; import kg.attractor.moneytransferapp.repository.AccountRepository; import kg.attractor.moneytransferapp.service.AccountService; import lombok.RequiredArgsConstructor; import lombok.extern.slf4j.Slf4j; import org.springframework.stereotype.Service;
import java.math.BigDecimal; import java.util.List; import java.util.NoSuchElementException; import java.util.Random;
@Slf4j @Service @RequiredArgsConstructor public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public void createAccount(
            User user,
            CurrencyType currency
    ) {

        if (
                accountRepository.countByUser(user) >= 3
        ) {

            log.warn(
                    "User {} tried to create more than 3 accounts",
                    user.getUsername()
            );

            throw new IllegalArgumentException(
                    "You can have no more than 3 accounts"
            );
        }

        if (
                accountRepository
                        .existsByUserAndCurrency(
                                user,
                                currency
                        )
        ) {

            log.warn(
                    "User {} already has account in {}",
                    user.getUsername(),
                    currency
            );

            throw new IllegalArgumentException(
                    "Account with this currency already exists"
            );
        }

        Account account =
                Account.builder()
                        .accountNumber(
                                generateAccountNumber()
                        )
                        .currency(
                                currency
                        )
                        .balance(
                                BigDecimal.ZERO
                        )
                        .user(
                                user
                        )
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
                                        "Account not found"
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
                    "This account does not belong to user"
            );
        }

        return account;
    }

    private String generateAccountNumber() {

        Random random =
                new Random();

        String accountNumber;

        do {

            int number =
                    100000
                            + random.nextInt(
                            900000
                    );

            accountNumber =
                    String.valueOf(
                            number
                    );

        } while (
                accountRepository
                        .existsByAccountNumber(
                                accountNumber
                        )
        );

        return accountNumber;
    }
}