package kg.attractor.moneytransferapp.model;

import jakarta.persistence.*;
import kg.attractor.moneytransferapp.model.enums.ServiceProviderType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "service_accounts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_service_provider_requisite",
                        columnNames = {
                                "provider",
                                "requisite"
                        }
                )
        }
)
public class ServiceAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    private ServiceProviderType provider;

    @Column(
            nullable = false,
            length = 50
    )
    private String requisite;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal balance;
}