package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.dto.UserDto;
import nl.novi.backendgarageservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<UserDto>> getAllUsers() {
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String username) {
        return new ResponseEntity(userService.getUserByName(username), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Validated @RequestBody UserDto userDto, BindingResult br) {
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
            String newId = userService.createUser(userDto);

            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/" + newId).toUriString());

            userDto.username = newId;

            return ResponseEntity.created(uri).body("User created successfully");
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable String username, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(username, userDto));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.deleteUser(username));
    }
}
