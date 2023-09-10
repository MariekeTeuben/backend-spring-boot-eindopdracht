package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.dto.RepairJobDto;
import nl.novi.backendgarageservice.service.RepairJobService;
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
@RequestMapping("/repairjobs")
public class RepairJobController {

    private final RepairJobService repairJobService;

    public RepairJobController(RepairJobService repairJobService) {
        this.repairJobService = repairJobService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<RepairJobDto>> getAllRepairJobs() {
        return new ResponseEntity(repairJobService.getAllRepairJobs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairJobDto> getRepairJobById(@PathVariable Long id) {
        return new ResponseEntity(repairJobService.getRepairJobById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createRepairJob(@Validated @RequestBody RepairJobDto repairJobDto, BindingResult br) {
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
            Long newId = repairJobService.createRepairJob(repairJobDto);

            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/" + newId).toUriString());

            repairJobDto.id = newId;

            return ResponseEntity.created(uri).body(repairJobDto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRepairJob(@PathVariable Long id, @RequestBody RepairJobDto repairJobDto) {
        return ResponseEntity.ok(repairJobService.updateRepairJob(id, repairJobDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRepairJob(@PathVariable Long id) {
        return ResponseEntity.ok(repairJobService.deleteRepairJob(id));
    }
}
