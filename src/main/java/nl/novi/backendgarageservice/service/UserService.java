package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.UserDto;
import nl.novi.backendgarageservice.exception.ResourceNotFoundException;
import nl.novi.backendgarageservice.model.Appointment;
import nl.novi.backendgarageservice.model.User;
import nl.novi.backendgarageservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepos;

    public UserService(UserRepository userRepos) {
        this.userRepos = userRepos;
    }


    public UserDto getUserByName(String username) {
        User user = userRepos.findById(username).orElseThrow(() -> new ResourceNotFoundException("User cannot be found"));

        UserDto userDto = new UserDto();
        userDto.username = user.getUsername();
        userDto.password = user.getPassword();

        if(user.getAppointments() != null) {
            for (Appointment appointment : user.getAppointments()) {
                userDto.appointments.add(appointment.getAppointmentDate());
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

            if(user.getAppointments() != null) {
                for (Appointment appointment : user.getAppointments()) {
                    userDto.appointments.add(appointment.getAppointmentDate());
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
        user.setPassword(userDto.password);

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
