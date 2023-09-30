package nl.novi.backendgarageservice.dto;

import nl.novi.backendgarageservice.model.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class InvoiceDto {
    public Long id;

    public LocalDate date;

    public Double tax;

    public Double totalPrice;

    public ArrayList<String> repairItems = new ArrayList<>();

    public String username;
}
