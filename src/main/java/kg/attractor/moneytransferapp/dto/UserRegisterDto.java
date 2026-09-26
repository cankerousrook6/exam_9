package kg.attractor.moneytransferapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {

    @NotBlank(message = "Username is required")
    @Size(
            min = 3,
            max = 30,
            message = "Username must contain from 3 to 30 characters"
    )
    private String username;

    @NotBlank(message = "Password is required")
    @Size(
            min = 6,
            max = 50,
            message = "Password must contain from 6 to 50 characters"
    )
    private String password;
}