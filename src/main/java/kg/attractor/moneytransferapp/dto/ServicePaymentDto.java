package kg.attractor.moneytransferapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServicePaymentDto {

    @NotNull(
            message = "{validation.senderAccount.required}"
    )
    private Long senderAccountId;

    @NotNull(
            message = "{validation.provider.required}"
    )
    private Long providerId;

    @NotBlank(
            message = "{validation.requisite.required}"
    )
    private String requisite;

    @NotNull(
            message = "{validation.amount.required}"
    )
    @DecimalMin(
            value = "0.01",
            message = "{validation.amount.min}"
    )
    private BigDecimal amount;
}