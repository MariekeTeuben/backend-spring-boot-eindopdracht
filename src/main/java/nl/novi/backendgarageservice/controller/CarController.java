package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.dto.AppointmentDto;
import nl.novi.backendgarageservice.dto.CarDto;
import nl.novi.backendgarageservice.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<CarDto>> getAllCars() {
        return new ResponseEntity(carService.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<CarDto> getCarByLicensePlate(@PathVariable String licensePlate) {
        return new ResponseEntity(carService.getCarByLicensePlate(licensePlate), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createCar(@Validated @RequestBody CarDto carDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            String newLicensePlate = carService.createCar(carDto);

            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/" + newLicensePlate).toUriString());

            carDto.licensePlate = newLicensePlate;

            return ResponseEntity.created(uri).body(carDto);
        }
    }

    @PutMapping("/{licensePlate}")
    public ResponseEntity<Object> updateCar(@PathVariable String licensePlate, @RequestBody CarDto carDto) {
        return ResponseEntity.ok(carService.updateCar(licensePlate, carDto));
    }

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<Object> deleteCar(@PathVariable String licensePlate) {
        return ResponseEntity.ok(carService.deleteCar(licensePlate));
    }
}