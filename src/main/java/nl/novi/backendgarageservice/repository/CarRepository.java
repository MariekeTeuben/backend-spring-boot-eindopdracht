package nl.novi.backendgarageservice.repository;

import nl.novi.backendgarageservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, String> {

    Optional<Car> findByLicensePlate(String licensePlate);
}
