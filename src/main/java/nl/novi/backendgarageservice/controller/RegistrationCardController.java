package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.dto.RegistrationCardDto;
import nl.novi.backendgarageservice.service.RegistrationCardService;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
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
@RequestMapping("/registration")
public class RegistrationCardController {

    private final RegistrationCardService registrationCardService;

    public RegistrationCardController(RegistrationCardService registrationCardService) {
        this.registrationCardService = registrationCardService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<RegistrationCardDto>> getAllRegistrationCards() {
        return new ResponseEntity(registrationCardService.getAllRegistrationCards(), HttpStatus.OK);
    }

    @GetMapping("/{chassisNumber}")
    public ResponseEntity<RegistrationCardDto> getRegistrationCardByChassisNumber(@PathVariable String chassisNumber) {
        return new ResponseEntity(registrationCardService.getRegistrationCardByChassisNumber(chassisNumber), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createRegistrationCard(@Validated @RequestBody RegistrationCardDto registrationCardDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            String newChassisNumber = registrationCardService.createRegistrationCard(registrationCardDto);

            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/" + newChassisNumber).toUriString());

            registrationCardDto.chassisNumber = newChassisNumber;

            return ResponseEntity.created(uri).body("Registration card created successfully");
        }
    }

    @PutMapping("/{chassisNumber}")
    public ResponseEntity<Object> updateRegistrationCard(@PathVariable String chassisNumber, @RequestBody RegistrationCardDto registrationCardDto) {
        return ResponseEntity.ok(registrationCardService.updateRegistrationCard(chassisNumber, registrationCardDto));
    }

    @DeleteMapping("/{chassisNumber}")
    public ResponseEntity<Object> deleteRegistrationCard(@PathVariable String chassisNumber) {
        return ResponseEntity.ok(registrationCardService.deleteRegistrationCard(chassisNumber));
    }


}
