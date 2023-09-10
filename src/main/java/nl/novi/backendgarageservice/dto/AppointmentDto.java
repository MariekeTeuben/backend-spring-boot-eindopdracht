package nl.novi.backendgarageservice.dto;

import jakarta.validation.constraints.NotNull;
import nl.novi.backendgarageservice.model.AppointmentType;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDto {
    public Long id;

    @NotNull
    public LocalDate appointmentDate;

    @NotNull
    public LocalTime appointmentTime;

    public AppointmentType appointmentType;
}
