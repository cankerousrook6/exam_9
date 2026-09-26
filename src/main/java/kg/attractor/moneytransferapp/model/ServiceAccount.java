package kg.attractor.moneytransferapp.model;

import jakarta.persistence.*;
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
                        name = "uq_service_account_provider_requisite",
                        columnNames = {
                                "provider_id",
                                "requisite"
                        }
                )
        }
)
public class ServiceAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne
    @JoinColumn(
            name = "provider_id",
            nullable = false
    )
    private ServiceProvider provider;
}