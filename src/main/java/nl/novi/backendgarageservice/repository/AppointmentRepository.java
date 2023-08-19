package nl.novi.backendgarageservice.repository;

import nl.novi.backendgarageservice.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
