package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.AppointmentDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Appointment;
import nl.novi.backendgarageservice.model.Car;
import nl.novi.backendgarageservice.model.User;
import nl.novi.backendgarageservice.repository.AppointmentRepository;
import nl.novi.backendgarageservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepos;
    private final UserRepository userRepos;

    public AppointmentService(AppointmentRepository appointmentRepos, UserRepository userRepos) {
        this.appointmentRepos = appointmentRepos;
        this.userRepos = userRepos;
    }

    public AppointmentDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment cannot be found"));

        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.id = appointment.getId();
        appointmentDto.appointmentDate = appointment.getAppointmentDate();
        appointmentDto.appointmentTime = appointment.getAppointmentTime();
        appointmentDto.appointmentType = appointment.getAppointmentType();
        appointmentDto.userName = appointment.getUser().getUsername();

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
            appointmentDto.userName = appointment.getUser().getUsername();

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

        User user = userRepos.findById(appointmentDto.userName).get();
        appointment.setUser(user);

        appointmentRepos.save(appointment);

        return appointment.getId();
    }

    public Object updateAppointment(Long id, AppointmentDto appointmentDto) {
        Optional<Appointment> appointment = appointmentRepos.findById(id);
        if (appointment.isEmpty()) {
            throw new ResourceNotFoundException("No appointment with id:" + id);
        } else {
            Appointment updatedAppointment = appointment.get();
            updatedAppointment.setAppointmentDate(appointmentDto.appointmentDate);
            updatedAppointment.setAppointmentTime(appointmentDto.appointmentTime);
            updatedAppointment.setAppointmentType(appointmentDto.appointmentType);
            appointmentRepos.save(updatedAppointment);
            return updatedAppointment;
        }
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
