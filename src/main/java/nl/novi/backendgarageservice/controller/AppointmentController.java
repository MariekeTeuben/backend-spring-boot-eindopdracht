package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.dto.AppointmentDto;
import nl.novi.backendgarageservice.model.Appointment;
import nl.novi.backendgarageservice.model.User;
import nl.novi.backendgarageservice.repository.AppointmentRepository;
import nl.novi.backendgarageservice.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<AppointmentDto>> getAllAppointments() {
        return new ResponseEntity(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
        return new ResponseEntity(appointmentService.getAppointmentById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createAppointment(@Validated @RequestBody AppointmentDto appointmentDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        else {
            Long newId = appointmentService.createAppointment(appointmentDto);

            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/" + newId).toUriString());

            appointmentDto.id = newId;

            return ResponseEntity.created(uri).body(appointmentDto);
        }

    }
}
