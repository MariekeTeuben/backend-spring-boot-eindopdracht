package nl.novi.backendgarageservice.dto;

import java.time.LocalDate;
import java.util.ArrayList;

public class InvoiceDto {
    public Long id;

    public LocalDate date;

    public Double tax;

    public Double total;

    public ArrayList<String> repairJobs = new ArrayList<>();

}
