package kg.attractor.moneytransferapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {

    @NotBlank(
            message = "{validation.username.required}"
    )
    @Size(
            min = 3,
            max = 30,
            message = "{validation.username.size}"
    )
    private String username;

    @NotBlank(
            message = "{validation.password.required}"
    )
    @Size(
            min = 6,
            max = 50,
            message = "{validation.password.size}"
    )
    private String password;
}