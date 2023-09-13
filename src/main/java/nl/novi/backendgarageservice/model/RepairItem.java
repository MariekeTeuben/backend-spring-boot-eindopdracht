package nl.novi.backendgarageservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "repairitems")
public class RepairItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private String itemType;

    private String itemDescription;

    private Integer itemQuantity;

    private Double itemPrice;

    @ManyToOne
    @JoinColumn(name="repairJob_id")
    private RepairJob repairJob;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public RepairJob getRepairJob() {
        return repairJob;
    }

    public void setRepairJob(RepairJob repairJob) {
        this.repairJob = repairJob;
    }
}
