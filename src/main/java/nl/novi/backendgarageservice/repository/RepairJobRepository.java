package nl.novi.backendgarageservice.repository;

import nl.novi.backendgarageservice.model.RepairJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairJobRepository extends JpaRepository<RepairJob, Long> {

}