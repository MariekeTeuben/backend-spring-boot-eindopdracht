package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.model.Car;
import nl.novi.backendgarageservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository repos;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(repos.findAll());
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        repos.save(car);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + car.getLicensePlate()).toUriString());
        return ResponseEntity.created(uri).body(car);
    }

}
