package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.CarDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Car;
import nl.novi.backendgarageservice.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepos;

    public CarService(CarRepository carRepos) {
        this.carRepos = carRepos;
    }

    public CarDto getCarByLicensePlate(String licensePlate) {
        Car car = carRepos.findById(licensePlate).orElseThrow(() -> new ResourceNotFoundException("Car cannot be found"));

        CarDto carDto = new CarDto();
        carDto.licensePlate = car.getLicensePlate();
        carDto.carBrand = car.getCarBrand();
        carDto.carModel = car.getCarModel();
        carDto.carType = car.getCarType();
        carDto.carColor = car.getCarColor();
        carDto.carStatus = car.getCarStatus();

        return carDto;
    }

    public List<CarDto> getAllCars() {
        ArrayList<CarDto> carDtoList = new ArrayList<>();
        Iterable<Car> allCars = carRepos.findAll();
        for (Car car: allCars) {
            CarDto carDto = new CarDto();

            carDto.licensePlate = car.getLicensePlate();
            carDto.carBrand = car.getCarBrand();
            carDto.carModel = car.getCarModel();
            carDto.carType = car.getCarType();
            carDto.carColor = car.getCarColor();
            carDto.carStatus = car.getCarStatus();

            carDtoList.add(carDto);
        }

        if (carDtoList.size() == 0) {
            throw new ResourceNotFoundException("Cars cannot be found");
        }

        return carDtoList;

    }

    public String createCar(CarDto carDto) {
        Car car = new Car();

        car.setLicensePlate(carDto.licensePlate);
        car.setCarBrand(carDto.carBrand);
        car.setCarModel(carDto.carModel);
        car.setCarType(carDto.carType);
        car.setCarColor(carDto.carColor);
        car.setCarStatus(carDto.carStatus);

        carRepos.save(car);

        return car.getLicensePlate();
    }

    public Object updateCar(String licensePlate, CarDto carDto) {
        Optional<Car> car = carRepos.findById(licensePlate);
        if (car.isEmpty()) {
            throw new ResourceNotFoundException("No car with license plate:" + licensePlate);
        } else {
            Car updatedCar = car.get();
            updatedCar.setCarBrand(carDto.carBrand);
            updatedCar.setCarModel(carDto.carModel);
            updatedCar.setCarType(carDto.carType);
            updatedCar.setCarColor(carDto.carColor);
            updatedCar.setCarStatus(carDto.carStatus);

            carRepos.save(updatedCar);
            return updatedCar;
        }
    }

    public Object deleteCar(String licensePlate) {
        Optional<Car> optionalCar = carRepos.findByLicensePlate(licensePlate);
        if (optionalCar.isEmpty()) {
            throw new ResourceNotFoundException("Car cannot be found");
        } else {
            Car car = optionalCar.get();
            carRepos.delete(car);
            return "Car deleted successfully";
        }
    }
}
