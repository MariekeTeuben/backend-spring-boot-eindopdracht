package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.model.Appointment;
import nl.novi.backendgarageservice.model.User;
import nl.novi.backendgarageservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository repos;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments() {
        return ResponseEntity.ok(repos.findAll());
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        repos.save(appointment);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + appointment.getId()).toUriString());
        return ResponseEntity.created(uri).body(appointment);
    }
}
