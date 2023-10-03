package nl.novi.backendgarageservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDto {

    @NotBlank
    public String username;

    @NotBlank
    @Size(min = 6, message = "The password should be 6 to 13 characters long")
    @Size(max = 13, message = "The password should be 6 to 13 characters long")
    public String password;
}
