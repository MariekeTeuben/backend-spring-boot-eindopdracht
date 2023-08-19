package nl.novi.backendgarageservice.repository;

import nl.novi.backendgarageservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, String> {
}
