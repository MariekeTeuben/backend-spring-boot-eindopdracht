package nl.novi.backendgarageservice.repository;

import nl.novi.backendgarageservice.model.Appointment;
import nl.novi.backendgarageservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    //List<Appointment> findByUser (User user);

}
