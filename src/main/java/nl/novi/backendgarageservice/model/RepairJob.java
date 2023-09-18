package nl.novi.backendgarageservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "repairjobs")
public class RepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;

    @ManyToOne
    private Invoice invoice;

    @OneToMany(mappedBy = "repairJob")
    @JsonIgnore
    private List<RepairItem> repairItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<RepairItem> getRepairItems() {
        return repairItems;
    }

    public void setRepairItems(List<RepairItem> repairItems) {
        this.repairItems = repairItems;
    }
}
