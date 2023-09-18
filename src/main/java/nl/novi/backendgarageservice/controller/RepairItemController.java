package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.dto.RepairItemDto;
import nl.novi.backendgarageservice.service.RepairItemService;
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
@RequestMapping("/repairitems")
public class RepairItemController {

    private final RepairItemService repairItemService;

    public RepairItemController(RepairItemService repairItemService) {
        this.repairItemService = repairItemService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<RepairItemDto>> getAllRepairItems() {
        return new ResponseEntity(repairItemService.getAllRepairItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairItemDto> getRepairItemById(@PathVariable Long id) {
        return new ResponseEntity(repairItemService.getRepairItemById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createRepairItem(@Validated @RequestBody RepairItemDto repairItemDto, BindingResult br) {
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
            Long newId = repairItemService.createRepairItem(repairItemDto);

            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/" + newId).toUriString());

            repairItemDto.id = newId;

            return ResponseEntity.created(uri).body(repairItemService.getRepairItemById(newId));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRepairItem(@PathVariable Long id, @RequestBody RepairItemDto repairItemDto) {
        return ResponseEntity.ok(repairItemService.updateRepairItem(id, repairItemDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRepairItem(@PathVariable Long id) {
        return ResponseEntity.ok(repairItemService.deleteRepairItem(id));
    }
}
