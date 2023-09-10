package nl.novi.backendgarageservice.dto;

import jakarta.validation.constraints.NotNull;

public class RepairItemDto {
    public Long id;

    @NotNull
    public String itemName;

    public String itemType;

    public String itemDescription;

    @NotNull
    public Integer itemQuantity;

    @NotNull
    public Double itemPrice;
}
