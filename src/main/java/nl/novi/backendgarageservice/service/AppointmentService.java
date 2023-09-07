package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.AppointmentDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Appointment;
import nl.novi.backendgarageservice.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepos;

    public AppointmentService(AppointmentRepository appointmentRepos) {
        this.appointmentRepos = appointmentRepos;
    }

    public AppointmentDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment cannot be found"));

        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.id = appointment.getId();
        appointmentDto.appointmentDate = appointment.getAppointmentDate();
        appointmentDto.appointmentTime = appointment.getAppointmentTime();
        appointmentDto.appointmentType = appointment.getAppointmentType();

        return appointmentDto;
    }

    public List<AppointmentDto> getAllAppointments() {
        ArrayList<AppointmentDto> appointmentDtoList = new ArrayList<>();
        Iterable<Appointment> allAppointments = appointmentRepos.findAll();
        for (Appointment appointment: allAppointments) {
            AppointmentDto appointmentDto = new AppointmentDto();

            appointmentDto.id = appointment.getId();
            appointmentDto.appointmentDate = appointment.getAppointmentDate();
            appointmentDto.appointmentTime = appointment.getAppointmentTime();
            appointmentDto.appointmentType = appointment.getAppointmentType();

            appointmentDtoList.add(appointmentDto);
        }

        if (appointmentDtoList.size() == 0) {
            throw new ResourceNotFoundException("Appointments cannot be found");
        }

        return appointmentDtoList;

    }

    public Long createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();

        appointment.setAppointmentDate(appointmentDto.appointmentDate);
        appointment.setAppointmentTime(appointmentDto.appointmentTime);
        appointment.setAppointmentType(appointmentDto.appointmentType);

        appointmentRepos.save(appointment);

        return appointment.getId();
    }


    public Object deleteAppointment(Long id) {
        Optional<Appointment> optionalAppointment = appointmentRepos.findById(id);
        if (optionalAppointment.isEmpty()) {
            throw new ResourceNotFoundException("Appointment cannot be found");
        } else {
            Appointment appointment = optionalAppointment.get();
            appointmentRepos.delete(appointment);
            return "Appointment deleted successfully";
        }
    }
}
