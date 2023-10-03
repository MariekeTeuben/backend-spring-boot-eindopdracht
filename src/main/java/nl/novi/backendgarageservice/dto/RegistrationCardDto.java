package nl.novi.backendgarageservice.dto;


import jakarta.validation.constraints.NotBlank;

public class RegistrationCardDto {

    @NotBlank
    public String chassisNumber;

    @NotBlank
    public String name;

    @NotBlank
    public String postalCode;
}
