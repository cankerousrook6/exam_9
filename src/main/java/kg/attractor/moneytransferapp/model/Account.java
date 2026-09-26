package kg.attractor.moneytransferapp.model;

import jakarta.persistence.*;
import lombok.*;
import kg.attractor.moneytransferapp.model.enums.CurrencyType;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_account_user_currency",
                        columnNames = {
                                "user_id",
                                "currency"
                        }
                )
        }
)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "account_number",
            nullable = false,
            unique = true,
            length = 6
    )
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 10
    )
    private CurrencyType currency;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
}