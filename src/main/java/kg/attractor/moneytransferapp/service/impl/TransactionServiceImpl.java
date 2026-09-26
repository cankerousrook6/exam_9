package kg.attractor.moneytransferapp.service.impl;

import kg.attractor.moneytransferapp.model.Account;
import kg.attractor.moneytransferapp.model.Transaction;
import kg.attractor.moneytransferapp.model.User;
import kg.attractor.moneytransferapp.model.enums.CurrencyType;
import kg.attractor.moneytransferapp.model.enums.TransactionStatus;
import kg.attractor.moneytransferapp.model.enums.TransactionType;
import kg.attractor.moneytransferapp.repository.AccountRepository;
import kg.attractor.moneytransferapp.repository.TransactionRepository;
import kg.attractor.moneytransferapp.service.AccountService;
import kg.attractor.moneytransferapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl
        implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public Transaction topUp(
            String accountNumber,
            BigDecimal amount
    ) {

        Account account =
                accountRepository
                        .findByAccountNumber(
                                accountNumber
                        )
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "topup.accountNotFound"
                                        )
                        );

        account.setBalance(
                account.getBalance()
                        .add(amount)
        );

        accountRepository.save(
                account
        );

        Transaction transaction =
                Transaction.builder()
                        .type(
                                TransactionType.TOP_UP
                        )
                        .status(
                                TransactionStatus.COMPLETED
                        )
                        .amount(
                                amount
                        )
                        .receivedAmount(
                                amount
                        )
                        .currency(
                                account.getCurrency()
                        )
                        .exchangeRate(
                                BigDecimal.ONE
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .receiverAccount(
                                account
                        )
                        .description(
                                "transaction.topUp"
                        )
                        .build();

        transactionRepository.save(
                transaction
        );

        log.info(
                "Account {} topped up. Amount={}",
                accountNumber,
                amount
        );

        return transaction;
    }

    @Override
    @Transactional
    public Transaction transfer(
            User user,
            Long senderAccountId,
            String receiverAccountNumber,
            BigDecimal amount
    ) {

        Account sender =
                accountService.getByIdAndUser(
                        senderAccountId,
                        user
                );

        Account receiver =
                accountRepository
                        .findByAccountNumber(
                                receiverAccountNumber
                        )
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "transfer.receiverNotFound"
                                        )
                        );

        if (
                sender.getId()
                        .equals(
                                receiver.getId()
                        )
        ) {

            throw new IllegalArgumentException(
                    "transfer.sameAccount"
            );
        }

        if (
                sender.getBalance()
                        .compareTo(amount)
                        < 0
        ) {

            throw new IllegalArgumentException(
                    "transfer.notEnoughMoney"
            );
        }

        BigDecimal exchangeRate =
                getExchangeRate(
                        sender.getCurrency(),
                        receiver.getCurrency()
                );

        BigDecimal receivedAmount =
                amount.multiply(
                                exchangeRate
                        )
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        BigDecimal amountInUsd =
                convertToUsd(
                        amount,
                        sender.getCurrency()
                );

        TransactionStatus status;

        if (
                amountInUsd.compareTo(
                        new BigDecimal("100")
                ) >= 0
        ) {

            status =
                    TransactionStatus.PENDING;

        } else {

            status =
                    TransactionStatus.COMPLETED;

            sender.setBalance(
                    sender.getBalance()
                            .subtract(amount)
            );

            receiver.setBalance(
                    receiver.getBalance()
                            .add(
                                    receivedAmount
                            )
            );

            accountRepository.save(
                    sender
            );

            accountRepository.save(
                    receiver
            );
        }

        Transaction transaction =
                Transaction.builder()
                        .type(
                                TransactionType.TRANSFER
                        )
                        .status(
                                status
                        )
                        .amount(
                                amount
                        )
                        .receivedAmount(
                                receivedAmount
                        )
                        .currency(
                                sender.getCurrency()
                        )
                        .exchangeRate(
                                exchangeRate
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .senderAccount(
                                sender
                        )
                        .receiverAccount(
                                receiver
                        )
                        .description(
                                "transaction.transfer"
                        )
                        .build();

        transactionRepository.save(
                transaction
        );

        log.info(
                "Transfer created. Sender={}, receiver={}, amount={}, status={}",
                sender.getAccountNumber(),
                receiver.getAccountNumber(),
                amount,
                status
        );

        return transaction;
    }

    @Override
    public List<Transaction> getAll() {

        return transactionRepository
                .findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Transaction> getPending() {

        return transactionRepository
                .findAllByStatusOrderByCreatedAtAsc(
                        TransactionStatus.PENDING
                );
    }

    @Override
    public Transaction getById(
            Long id
    ) {

        return transactionRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new NoSuchElementException(
                                        "admin.transactionNotFound"
                                )
                );
    }

    @Override
    @Transactional
    public void approve(
            Long id
    ) {

        Transaction transaction =
                getById(id);

        if (
                transaction.getStatus()
                        != TransactionStatus.PENDING
        ) {

            throw new IllegalArgumentException(
                    "admin.notPending"
            );
        }

        if (
                transaction.getType()
                        != TransactionType.TRANSFER
        ) {

            throw new IllegalArgumentException(
                    "admin.onlyTransfer"
            );
        }

        Account sender =
                transaction.getSenderAccount();

        Account receiver =
                transaction.getReceiverAccount();

        if (
                sender.getBalance()
                        .compareTo(
                                transaction.getAmount()
                        )
                        < 0
        ) {

            log.warn(
                    "Transfer {} cannot be approved. Not enough money",
                    id
            );

            throw new IllegalArgumentException(
                    "admin.notEnoughMoney"
            );
        }

        sender.setBalance(
                sender.getBalance()
                        .subtract(
                                transaction.getAmount()
                        )
        );

        receiver.setBalance(
                receiver.getBalance()
                        .add(
                                transaction.getReceivedAmount()
                        )
        );

        accountRepository.save(
                sender
        );

        accountRepository.save(
                receiver
        );

        transaction.setStatus(
                TransactionStatus.COMPLETED
        );

        transactionRepository.save(
                transaction
        );

        log.info(
                "Transfer {} approved and completed",
                id
        );
    }

    @Override
    public List<Transaction> getUserTransactions(
            User user,
            LocalDate dateFrom,
            LocalDate dateTo,
            String sort
    ) {

        List<Transaction> transactions =
                transactionRepository
                        .findAllBySenderAccount_UserOrReceiverAccount_UserOrderByCreatedAtDesc(
                                user,
                                user
                        )
                        .stream()
                        .filter(
                                transaction ->
                                        transaction.getStatus()
                                                == TransactionStatus.COMPLETED
                        )
                        .filter(
                                transaction ->
                                        dateFrom == null
                                                || !transaction
                                                .getCreatedAt()
                                                .toLocalDate()
                                                .isBefore(
                                                        dateFrom
                                                )
                        )
                        .filter(
                                transaction ->
                                        dateTo == null
                                                || !transaction
                                                .getCreatedAt()
                                                .toLocalDate()
                                                .isAfter(
                                                        dateTo
                                                )
                        )
                        .toList();

        if (
                "currency".equals(sort)
        ) {

            return transactions
                    .stream()
                    .sorted(
                            Comparator.comparing(
                                    transaction ->
                                            transaction
                                                    .getCurrency()
                                                    .name()
                            )
                    )
                    .toList();
        }

        return transactions
                .stream()
                .sorted(
                        Comparator.comparing(
                                Transaction::getCreatedAt
                        ).reversed()
                )
                .toList();
    }

    private BigDecimal getExchangeRate(
            CurrencyType from,
            CurrencyType to
    ) {

        if (from == to) {
            return BigDecimal.ONE;
        }

        BigDecimal amountInUsd =
                convertToUsd(
                        BigDecimal.ONE,
                        from
                );

        return convertFromUsd(
                amountInUsd,
                to
        );
    }

    private BigDecimal convertToUsd(
            BigDecimal amount,
            CurrencyType currency
    ) {

        return switch (currency) {

            case USD ->
                    amount;

            case EUR ->
                    amount.multiply(
                            new BigDecimal("1.10")
                    );

            case KGS ->
                    amount.divide(
                            new BigDecimal("87.00"),
                            6,
                            RoundingMode.HALF_UP
                    );
        };
    }

    private BigDecimal convertFromUsd(
            BigDecimal amount,
            CurrencyType currency
    ) {

        return switch (currency) {

            case USD ->
                    amount;

            case EUR ->
                    amount.divide(
                            new BigDecimal("1.10"),
                            6,
                            RoundingMode.HALF_UP
                    );

            case KGS ->
                    amount.multiply(
                            new BigDecimal("87.00")
                    );
        };
    }
}