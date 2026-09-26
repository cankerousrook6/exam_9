package kg.attractor.moneytransferapp.service;

import kg.attractor.moneytransferapp.model.Transaction;
import kg.attractor.moneytransferapp.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    Transaction topUp(
            String accountNumber,
            BigDecimal amount
    );

    Transaction transfer(
            User user,
            Long senderAccountId,
            String receiverAccountNumber,
            BigDecimal amount
    );

    List<Transaction> getAll();

    List<Transaction> getPending();

    Transaction getById(
            Long id
    );

    void approve(
            Long id
    );

    List<Transaction> getUserTransactions(
            User user,
            LocalDate dateFrom,
            LocalDate dateTo,
            String sort
    );
}