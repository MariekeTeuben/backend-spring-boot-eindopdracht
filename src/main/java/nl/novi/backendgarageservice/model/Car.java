package nl.novi.backendgarageservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    private String licensePlate;

    private String carBrand;

    private String carModel;

    @Enumerated(EnumType.STRING)
    private CarType carType;

    private String carColor;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private RegistrationCard registrationCard;

    @ManyToOne
    private User user;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RegistrationCard getRegistrationCard() {
        return registrationCard;
    }

    public void setRegistrationCard(RegistrationCard registrationCard) {
        this.registrationCard = registrationCard;
    }
}

