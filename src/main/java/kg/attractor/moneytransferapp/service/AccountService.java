package kg.attractor.moneytransferapp.service;
import kg.attractor.moneytransferapp.model.Account; import kg.attractor.moneytransferapp.model.User; import kg.attractor.moneytransferapp.model.enums.CurrencyType;
import java.util.List;
public interface AccountService {
    void createAccount(
            User user,
            CurrencyType currency
    );

    List<Account> getUserAccounts(
            User user
    );

    Account getById(
            Long id
    );

    Account getByIdAndUser(
            Long id,
            User user
    );
}