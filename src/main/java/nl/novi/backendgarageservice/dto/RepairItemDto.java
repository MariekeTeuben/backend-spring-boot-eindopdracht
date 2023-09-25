package nl.novi.backendgarageservice.dto;

import jakarta.validation.constraints.NotNull;
import nl.novi.backendgarageservice.model.Invoice;

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

    public Invoice invoice;

    public Long invoiceId;
}
