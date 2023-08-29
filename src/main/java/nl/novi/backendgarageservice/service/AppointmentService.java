package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.AppointmentDto;
import nl.novi.backendgarageservice.model.Appointment;
import nl.novi.backendgarageservice.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    private final AppointmentRepository repos;

    public AppointmentService(AppointmentRepository repos) {
        this.repos = repos;
    }

    public Long createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();

        appointment.setAppointmentDate(appointmentDto.appointmentDate);
        appointment.setAppointmentTime(appointmentDto.appointmentTime);
        appointment.setAppointmentType(appointmentDto.appointmentType);

        repos.save(appointment);

        return appointment.getId();
    }

}
