package kg.attractor.moneytransferapp.repository;
import kg.attractor.moneytransferapp.model.Account; import kg.attractor.moneytransferapp.model.User; import kg.attractor.moneytransferapp.model.enums.CurrencyType; import org.springframework.data.jpa.repository.JpaRepository; import org.springframework.stereotype.Repository;
import java.util.List;
@Repository public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUser(
            User user
    );

    boolean existsByAccountNumber(
            String accountNumber
    );

    boolean existsByUserAndCurrency(
            User user,
            CurrencyType currency
    );

    long countByUser(
            User user
    );
}