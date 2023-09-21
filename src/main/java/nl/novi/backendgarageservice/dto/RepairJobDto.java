package nl.novi.backendgarageservice.dto;

import nl.novi.backendgarageservice.model.Invoice;

import java.util.ArrayList;

public class RepairJobDto {
    public Long id;

    public String jobName;

    public ArrayList<String> repairItems = new ArrayList<>();

    public Invoice invoice;

    public Long invoiceId;
}
