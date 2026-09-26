package kg.attractor.moneytransferapp.service;

import kg.attractor.moneytransferapp.model.Transaction;
import kg.attractor.moneytransferapp.model.User;

import java.math.BigDecimal;

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
}