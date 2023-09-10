package nl.novi.backendgarageservice.dto;

import nl.novi.backendgarageservice.model.CarStatus;
import nl.novi.backendgarageservice.model.CarType;

public class CarDto {
    public String licensePlate;

    public String carBrand;

    public String carModel;

    public CarType carType;

    public String carColor;

    public CarStatus carStatus;
}
