package nl.novi.backendgarageservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import nl.novi.backendgarageservice.model.AppointmentType;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDto {
    public Long id;

    @FutureOrPresent
    public LocalDate appointmentDate;

    public LocalTime appointmentTime;

    public AppointmentType appointmentType;

    @NotBlank
    public String userName;

}
