package nl.novi.backendgarageservice.dto;

import nl.novi.backendgarageservice.model.AppointmentType;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDto {
    public Long id;

    public LocalDate appointmentDate;

    public LocalTime appointmentTime;

    public AppointmentType appointmentType;
}
