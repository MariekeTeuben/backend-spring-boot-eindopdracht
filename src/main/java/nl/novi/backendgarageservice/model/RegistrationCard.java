package nl.novi.backendgarageservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "registrations")
public class RegistrationCard {

    @Id
    private String chassisNumber;

    private String name;

    private String postalCode;

    @OneToOne(mappedBy = "registrationCard")
    private Car car;

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
