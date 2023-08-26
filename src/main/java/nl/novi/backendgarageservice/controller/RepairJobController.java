package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.model.RepairJob;
import nl.novi.backendgarageservice.repository.RepairJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/repairjobs")
public class RepairJobController {
    @Autowired
    private RepairJobRepository repos;

    @GetMapping
    public ResponseEntity<List<RepairJob>> getAllRepairJobs() {
        return ResponseEntity.ok(repos.findAll());
    }

    @PostMapping
    public ResponseEntity<RepairJob> createRepairJob(@RequestBody RepairJob repairjob) {
        repos.save(repairjob);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + repairjob.getId()).toUriString());
        return ResponseEntity.created(uri).body(repairjob);
    }

}
