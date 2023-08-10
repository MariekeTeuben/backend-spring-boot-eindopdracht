package nl.novi.backendgarageservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue
    private long id;

    private LocalDate date;

    private LocalTime time;

    private String typeOfMaintenance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getTypeOfMaintenance() {
        return typeOfMaintenance;
    }

    public void setTypeOfMaintenance(String typeOfMaintenance) {
        this.typeOfMaintenance = typeOfMaintenance;
    }
}
