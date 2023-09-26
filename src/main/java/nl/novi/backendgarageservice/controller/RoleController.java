package nl.novi.backendgarageservice.controller;

import nl.novi.backendgarageservice.dto.RoleDto;
import nl.novi.backendgarageservice.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<ArrayList<RoleDto>> getAllRoles() {
        return new ResponseEntity(roleService.getAllRoles(), HttpStatus.OK);
    }
}
