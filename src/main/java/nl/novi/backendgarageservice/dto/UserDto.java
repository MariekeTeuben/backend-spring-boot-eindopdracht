package nl.novi.backendgarageservice.dto;

import nl.novi.backendgarageservice.model.Appointment;
import nl.novi.backendgarageservice.model.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDto {

    public String username;

    public String password;

    public ArrayList<String> cars = new ArrayList<>();

    public ArrayList<LocalDate> appointments = new ArrayList<>();

    public ArrayList<Long> invoices = new ArrayList<>();
}
