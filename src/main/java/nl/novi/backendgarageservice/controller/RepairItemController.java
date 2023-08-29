package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.model.RepairItem;
import nl.novi.backendgarageservice.repository.RepairItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/repairitems")
public class RepairItemController {

    @Autowired
    private RepairItemRepository repos;

    @GetMapping
    public ResponseEntity<List<RepairItem>> getAllRepairItems() {
        return ResponseEntity.ok(repos.findAll());
    }

    @PostMapping
    public ResponseEntity<RepairItem> createRepairItem(@RequestBody RepairItem repairitem) {
        repos.save(repairitem);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + repairitem.getId()).toUriString());
        return ResponseEntity.created(uri).body(repairitem);
    }
}
