package nl.novi.backendgarageservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;


public class UserDto {

    @NotBlank
    public String username;

    @NotBlank
    @Size(min = 6, message = "The password should be 6 to 13 characters long")
    @Size(max = 13, message = "The password should be 6 to 13 characters long")
    public String password;

    public ArrayList<String> roles = new ArrayList<>();

    public ArrayList<String> cars = new ArrayList<>();

    public ArrayList<LocalDate> appointments = new ArrayList<>();

    public ArrayList<Long> invoices = new ArrayList<>();
}
