package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.UserDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.*;
import nl.novi.backendgarageservice.repository.RoleRepository;
import nl.novi.backendgarageservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepos;
    private final RoleRepository roleRepos;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepos, RoleRepository roleRepos, PasswordEncoder encoder) {
        this.userRepos = userRepos;
        this.roleRepos = roleRepos;
        this.encoder = encoder;
    }


    public UserDto getUserByName(String username) {
        User user = userRepos.findById(username).orElseThrow(() -> new ResourceNotFoundException("User cannot be found"));

        UserDto userDto = new UserDto();
        userDto.username = user.getUsername();
        userDto.password = user.getPassword();

        if(user.getCars() != null) {
            for (Car car : user.getCars()) {
                userDto.cars.add(car.getLicensePlate());
            }
        }

        if(user.getAppointments() != null) {
            for (Appointment appointment : user.getAppointments()) {
                userDto.appointments.add(appointment.getAppointmentDate());
            }
        }

        if(user.getInvoices() != null) {
            for(Invoice invoice : user.getInvoices()) {
                userDto.invoices.add(invoice.getId());
            }
        }

        return userDto;
    }

    public List<UserDto> getAllUsers() {
        ArrayList<UserDto> userDtoList = new ArrayList<>();
        Iterable<User> allUsers = userRepos.findAll();
        for (User user: allUsers) {
            UserDto userDto = new UserDto();

            userDto.username = user.getUsername();
            userDto.password = user.getPassword();

            if(user.getCars() != null) {
                for (Car car : user.getCars()) {
                    userDto.cars.add(car.getLicensePlate());
                }
            }

            if(user.getAppointments() != null) {
                for (Appointment appointment : user.getAppointments()) {
                    userDto.appointments.add(appointment.getAppointmentDate());
                }
            }

            if(user.getInvoices() != null) {
                for(Invoice invoice : user.getInvoices()) {
                    userDto.invoices.add(invoice.getId());
                }
            }

            userDtoList.add(userDto);
        }

        if (userDtoList.size() == 0) {
            throw new ResourceNotFoundException("Users cannot be found");
        }

        return userDtoList;
    }

    public String createUser(UserDto userDto) {
        User user = new User();

        user.setUsername(userDto.username);
        user.setPassword(encoder.encode(userDto.password));

        List<Role> userRoles = new ArrayList<>();
        for (String rolename : userDto.roles) {
            Optional<Role> or = roleRepos.findById("ROLE_" + rolename);

            userRoles.add(or.get());
        }

        user.setRoles(userRoles);

        userRepos.save(user);

        userDto.username = user.getUsername();

        return user.getUsername();
    }

    public Object updateUser(String username, UserDto userDto) {
        Optional<User> user = userRepos.findById(username);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("No user with username:" + username);
        } else {
            User updatedUser = user.get();
            updatedUser.setUsername(userDto.username);
            updatedUser.setPassword(userDto.password);
            userRepos.save(updatedUser);

            return updatedUser;
        }
    }

    public Object deleteUser(String username) {
        Optional<User> optionalUser = userRepos.findById(username);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User cannot be found");
        } else {
            User user = optionalUser.get();
            userRepos.delete(user);
            return "User deleted successfully";
        }
    }

}
