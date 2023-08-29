package nl.novi.backendgarageservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    private String licensePlate;

    private String brand;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;


    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public CarStatus getCarStatus() {
        return carstatus;
    }

    public void setCarStatus(CarStatus carstatus) {
        this.carstatus = carstatus;
    }
}

