package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.CarDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Car;
import nl.novi.backendgarageservice.model.User;
import nl.novi.backendgarageservice.repository.CarRepository;
import nl.novi.backendgarageservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepos;
    private final UserRepository userRepos;

    public CarService(CarRepository carRepos, UserRepository userRepos) {
        this.carRepos = carRepos;
        this.userRepos = userRepos;
    }

    public CarDto getCarByLicensePlate(String licensePlate) {
        Car car = carRepos.findByLicensePlate(licensePlate).orElseThrow(() -> new ResourceNotFoundException("Car cannot be found"));

        CarDto carDto = new CarDto();
        carDto.licensePlate = car.getLicensePlate();
        carDto.carBrand = car.getCarBrand();
        carDto.carModel = car.getCarModel();
        carDto.carType = car.getCarType();
        carDto.carColor = car.getCarColor();
        carDto.carStatus = car.getCarStatus();
        carDto.userName = car.getUser().getUsername();

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
            carDto.userName = car.getUser().getUsername();

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

        User user = userRepos.findById(carDto.userName).get();
        car.setUser(user);

        carRepos.save(car);

        carDto.licensePlate = car.getLicensePlate();

        return car.getLicensePlate();
    }

    public Object updateCar(String licensePlate, CarDto carDto) {
        Optional<Car> car = carRepos.findByLicensePlate(licensePlate);
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
