package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.CarDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Car;
import nl.novi.backendgarageservice.model.CarStatus;
import nl.novi.backendgarageservice.model.CarType;
import nl.novi.backendgarageservice.model.User;
import nl.novi.backendgarageservice.repository.CarRepository;
import nl.novi.backendgarageservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CarServiceTest {

    @Mock
    CarRepository carRepos;

    @Mock
    UserRepository userRepos;

    @Mock
    UserService userService;

    @InjectMocks
    CarService carService;

    @Captor
    ArgumentCaptor<Car> carArgumentCaptor;

    List<Car> cars = new ArrayList<>();
    List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setUsername("maarten");
        user1.setPassword("utrecht");
        users.add(user1);

        User user2 = new User();
        user2.setUsername("luc");
        user2.setPassword("bilthoven");
        users.add(user2);

        Car car1 = new Car();
        car1.setLicensePlate("XK-783-P");
        car1.setCarBrand("Volkswagen");
        car1.setCarModel("T-roc");
        car1.setCarType(CarType.HATCHBACK);
        car1.setCarColor("blue");
        car1.setCarStatus(CarStatus.CHECKED_IN);
        car1.setUser(user1);
        cars.add(car1);

        Car car2 = new Car();
        car2.setLicensePlate("XX-781-G");
        car2.setCarBrand("Ford");
        car2.setCarModel("Ecosport");
        car2.setCarType(CarType.HATCHBACK);
        car2.setCarColor("red");
        car2.setCarStatus(CarStatus.MOT);
        car2.setUser(user2);
        cars.add(car2);

        Car car3 = new Car();
        car3.setLicensePlate("XH-781-F");
        car3.setCarBrand("Opel");
        car3.setCarModel("Astra");
        car3.setCarType(CarType.STATION);
        car3.setCarColor("green");
        car3.setCarStatus(CarStatus.INSPECTION);
        car3.setUser(user2);
        cars.add(car3);

        Car car4 = new Car();
        car4.setLicensePlate("PZ-897-N");
        car4.setCarBrand("Renault");
        car4.setCarModel("Scenic");
        car4.setCarType(CarType.HATCHBACK);
        car4.setCarColor("grey");
        car4.setCarStatus(CarStatus.REPAIR);
        car4.setUser(user2);
        cars.add(car4);
    }

    @Test
    void getCarByLicensePlate() {
        when(carRepos.findById("XK-783-P")).thenReturn(Optional.of(cars.get(0)));

        CarDto carDto = carService.getCarByLicensePlate("XK-783-P");

        assertEquals(cars.get(0).getCarBrand(), carDto.carBrand);
    }

    @Test
    void getCarByLicensePlateThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> carService.getCarByLicensePlate("XH-780-F"));
    }

    @Test
    void getAllCars() {

        when(carRepos.findAll()).thenReturn(cars);

        List<CarDto> carDtoList = carService.getAllCars();

        assertEquals(cars.size(), carDtoList.size());

    }

    @Test
    void createCar() {
        CarDto carDto5 = new CarDto();
        carDto5.licensePlate = "RB-765-G";
        carDto5.carBrand = "Mini";
        carDto5.carModel = "Cooper";
        carDto5.carType = CarType.COUPE;
        carDto5.carColor = "black";
        carDto5.carStatus = CarStatus.INSPECTION;
        carDto5.userName = "maarten";

        Car car5 = new Car();
        car5.setLicensePlate(carDto5.licensePlate);
        car5.setCarBrand(carDto5.carBrand);
        car5.setCarModel(carDto5.carModel);
        car5.setCarType(carDto5.carType);
        car5.setCarColor(carDto5.carColor);
        car5.setCarStatus(carDto5.carStatus);
        car5.setUser(car5.getUser());

        when(userRepos.findById("maarten")).thenReturn(Optional.of(users.get(0)));
        when(carRepos.save(car5)).thenReturn(car5);

        carService.createCar(carDto5);
        verify(carRepos, times(1)).save(carArgumentCaptor.capture());
        Car savedCar = carArgumentCaptor.getValue();

        assertEquals(carDto5.licensePlate, savedCar.getLicensePlate());
        assertEquals(carDto5.carBrand, savedCar.getCarBrand());
        assertEquals(carDto5.carModel, savedCar.getCarModel());
        assertEquals(carDto5.carType, savedCar.getCarType());
        assertEquals(carDto5.carColor, savedCar.getCarColor());
        assertEquals(carDto5.carStatus, savedCar.getCarStatus());
        assertEquals(carDto5.userName, savedCar.getUser().getUsername());
    }

//    @Test
//    void updateCar() {
//        String licensePlate = "PZ-897-N";
//        CarDto carDto5 = new CarDto();
//        carDto5.licensePlate = "PZ-897-N";
//        carDto5.carBrand = "Mini";
//        carDto5.carModel = "Cooper";
//        carDto5.carType = CarType.COUPE;
//        carDto5.carColor = "black";
//        carDto5.carStatus = CarStatus.INSPECTION;
//        carDto5.userName = "maarten";
//
//        Car existingCar = new Car();
//        existingCar.setLicensePlate(licensePlate);
//        existingCar.setCarBrand("Vorige");
//        existingCar.setCarModel("Model");
//        existingCar.setCarType(CarType.MPV);
//        existingCar.setCarColor("red");
//        existingCar.setCarStatus(CarStatus.CHECKED_IN);
//        existingCar.setUser(existingCar.getUser());
//
//        when(carRepos.findByLicensePlate(licensePlate)).thenReturn(Optional.of(existingCar));
//        when(carRepos.save(any(Car.class))).thenReturn(existingCar);
//
//        CarDto updatedCar = (CarDto) carService.updateCar(licensePlate, carDto5);
//        verify(carRepos, times(1)).findById(licensePlate);
//        verify(carRepos, times(1)).save(any(Car.class));
//
//        assertNotNull(updatedCar);
//        assertEquals(carDto5.licensePlate, updatedCar.licensePlate);
//        assertEquals(carDto5.carBrand, updatedCar.carBrand);
//        assertEquals(carDto5.carModel, updatedCar.carModel);
//        assertEquals(carDto5.carType, updatedCar.carType);
//        assertEquals(carDto5.carColor, updatedCar.carColor);
//        assertEquals(carDto5.carStatus, updatedCar.carStatus);
//        assertEquals(carDto5.userName, updatedCar.userName);
//    }

    @Test
    void deleteCar() {
        when(carRepos.findByLicensePlate("XH-781-F")).thenReturn(Optional.of(cars.get(2)));

        carService.deleteCar("XH-781-F");

        verify(carRepos).delete(cars.get(2));
    }

    @Test
    void deleteCarThrowsException() {
        when(carRepos.findByLicensePlate("KX-978-P")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            carService.deleteCar("KX-978-P");
        });
    }
}