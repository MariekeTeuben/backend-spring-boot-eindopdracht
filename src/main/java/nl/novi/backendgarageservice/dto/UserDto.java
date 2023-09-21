package nl.novi.backendgarageservice.dto;

import nl.novi.backendgarageservice.model.Appointment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDto {

    public String username;

    public String password;

    public ArrayList<LocalDate> appointments = new ArrayList<>();
}
