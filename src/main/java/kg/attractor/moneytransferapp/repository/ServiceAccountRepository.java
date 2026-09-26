package kg.attractor.moneytransferapp.repository;

import kg.attractor.moneytransferapp.model.ServiceAccount;
import kg.attractor.moneytransferapp.model.enums.ServiceProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceAccountRepository
        extends JpaRepository<ServiceAccount, Long> {

    Optional<ServiceAccount> findByProviderAndRequisite(
            ServiceProviderType provider,
            String requisite
    );
}