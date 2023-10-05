package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.CarDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Car;
import nl.novi.backendgarageservice.model.CarStatus;
import nl.novi.backendgarageservice.model.CarType;
import nl.novi.backendgarageservice.model.User;
import nl.novi.backendgarageservice.repository.CarRepository;
import nl.novi.backendgarageservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
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
        user1.setUsername("bob");
        user1.setPassword("westbroek");

        User user2 = new User();
        user2.setUsername("frits");
        user2.setPassword("naarden");

        Car car1 = new Car();
        car1.setLicensePlate("XK-783-P");
        car1.setCarBrand("Volkswagen");
        car1.setCarModel("T-roc");
        car1.setCarType(CarType.HATCHBACK);
        car1.setCarColor("blue");
        car1.setCarStatus(CarStatus.CHECKED_IN);
        car1.setUser(new User());
        cars.add(car1);

        Car car2 = new Car();
        car2.setLicensePlate("XX-781-G");
        car2.setCarBrand("Ford");
        car2.setCarModel("Ecosport");
        car2.setCarType(CarType.HATCHBACK);
        car2.setCarColor("red");
        car2.setCarStatus(CarStatus.MOT);
        car2.setUser(new User());
        cars.add(car2);

        Car car3 = new Car();
        car3.setLicensePlate("XH-781-F");
        car3.setCarBrand("Opel");
        car3.setCarModel("Astra");
        car3.setCarType(CarType.STATION);
        car3.setCarColor("green");
        car3.setCarStatus(CarStatus.INSPECTION);
        car3.setUser(new User());
        cars.add(car3);

        Car car4 = new Car();
        car4.setLicensePlate("PZ-897-N");
        car4.setCarBrand("Renault");
        car4.setCarModel("Scenic");
        car4.setCarType(CarType.HATCHBACK);
        car4.setCarColor("grey");
        car4.setCarStatus(CarStatus.REPAIR);
        car4.setUser(new User());
        cars.add(car4);

    }

//    @AfterEach
//    void tearDown() {
//    }

//    @Test
//    void getCarByLicensePlate() {
//        when(carRepos.findById("RB-765-G")).thenReturn(Optional.of(cars.get(0)));
//
//        CarDto carDto = carService.getCarByLicensePlate("RB-765-G");
//
//        assertEquals(cars.get(0).getCarBrand(), carDto.carBrand);
//
//    }
//
//    @Test
//    void getAllCars() {
//
//        when(carRepos.findAll()).thenReturn(cars);
//
//        List<CarDto> carDtoList = carService.getAllCars();
//
//        assertEquals(cars.size(), carDtoList.size());
//
//    }
//
//    @Test
//    void getCarByLicensePlateThrowsException() {
//        assertThrows(ResourceNotFoundException.class, () -> carService.getCarByLicensePlate("XH-780-F"));
//    }
//
//
//    @Test
//    void createCar() {
//        CarDto carDto5 = new CarDto();
//        carDto5.licensePlate = "RB-765-G";
//        carDto5.carBrand = "Mini";
//        carDto5.carModel = "Cooper";
//        carDto5.carType = CarType.COUPE ;
//        carDto5.carColor = "black";
//        carDto5.carStatus = CarStatus.INSPECTION;
//        carDto5.userName = "Marieke";
//
//        Car car = new Car();
//        car.setLicensePlate(carDto5.licensePlate);
//        car.setCarBrand(carDto5.carBrand);
//        car.setCarModel(carDto5.carModel);
//        car.setCarType(carDto5.carType);
//        car.setCarColor(carDto5.carColor);
//        car.setCarStatus(carDto5.carStatus);
//        car.setUser(new User().setUsername());
//
//        when(carRepos.save(any(Car.class))).thenReturn(car);
//
//        carService.createCar(carDto5);
//        verify(carRepos, times(1)).save(carArgumentCaptor.capture());
//        Car savedCar = carArgumentCaptor.getValue();
//
//        assertEquals(carDto5.licensePlate, savedCar.getLicensePlate());
//        assertEquals(carDto5.carBrand, savedCar.getCarBrand());
//        assertEquals(carDto5.carModel, savedCar.getCarModel());
//        assertEquals(carDto5.carType, savedCar.getCarType());
//        assertEquals(carDto5.carColor, savedCar.getCarColor());
//        assertEquals(carDto5.carStatus, savedCar.getCarStatus());
//        assertEquals(carDto5.userName, savedCar.getUser().getUsername());
//
//    }
//
//    @Test
//    void updateCar() {
//    }
//
//    @Test
//    void deleteCar() {
//    }
//}