package kg.attractor.moneytransferapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferDto {

    @NotNull(
            message = "{validation.senderAccount.required}"
    )
    private Long senderAccountId;

    @NotBlank(
            message = "{validation.receiverAccount.required}"
    )
    @Pattern(
            regexp = "\\d{6}",
            message = "{validation.accountNumber.format}"
    )
    private String receiverAccountNumber;

    @NotNull(
            message = "{validation.amount.required}"
    )
    @DecimalMin(
            value = "0.01",
            message = "{validation.amount.min}"
    )
    private BigDecimal amount;
}