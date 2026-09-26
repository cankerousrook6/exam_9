package kg.attractor.moneytransferapp.repository;

import kg.attractor.moneytransferapp.model.Transaction;
import kg.attractor.moneytransferapp.model.User;
import kg.attractor.moneytransferapp.model.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByOrderByCreatedAtDesc();

    List<Transaction> findAllByStatusOrderByCreatedAtAsc(
            TransactionStatus status
    );

    List<Transaction>
    findAllBySenderAccount_UserOrReceiverAccount_UserOrderByCreatedAtDesc(
            User senderUser,
            User receiverUser
    );
}