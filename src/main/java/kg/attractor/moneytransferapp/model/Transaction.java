package kg.attractor.moneytransferapp.model;

import jakarta.persistence.*;
import lombok.*;
import kg.attractor.moneytransferapp.model.enums.CurrencyType;
import kg.attractor.moneytransferapp.model.enums.TransactionStatus;
import kg.attractor.moneytransferapp.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private TransactionStatus status;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal amount;

    @Column(
            name = "received_amount",
            precision = 19,
            scale = 2
    )
    private BigDecimal receivedAmount;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 10
    )
    private CurrencyType currency;

    @Column(
            name = "exchange_rate",
            precision = 19,
            scale = 6
    )
    private BigDecimal exchangeRate;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(
            name = "sender_account_id"
    )
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(
            name = "receiver_account_id"
    )
    private Account receiverAccount;

    @Column(
            length = 255
    )
    private String description;
}