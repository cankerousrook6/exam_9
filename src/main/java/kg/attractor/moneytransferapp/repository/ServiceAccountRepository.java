package kg.attractor.moneytransferapp.repository;

import kg.attractor.moneytransferapp.model.ServiceAccount;
import kg.attractor.moneytransferapp.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceAccountRepository
        extends JpaRepository<ServiceAccount, Long> {

    Optional<ServiceAccount> findByProviderAndRequisite(
            ServiceProvider provider,
            String requisite
    );
}