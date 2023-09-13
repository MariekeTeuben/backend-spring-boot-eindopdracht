package nl.novi.backendgarageservice.dto;

import java.util.ArrayList;

public class RepairJobDto {
    public Long id;

    public String jobName;

    public ArrayList<String> repairItems = new ArrayList<>();
}
