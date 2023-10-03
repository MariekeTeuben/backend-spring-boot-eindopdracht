package nl.novi.backendgarageservice.dto;

import jakarta.validation.constraints.NotBlank;
import nl.novi.backendgarageservice.model.*;


import java.time.LocalDate;

public class CarDto {
    @NotBlank
    public String licensePlate;

    public String carBrand;

    public String carModel;

    public CarType carType;

    public String carColor;

    public CarStatus carStatus;

    @NotBlank
    public String userName;
}
