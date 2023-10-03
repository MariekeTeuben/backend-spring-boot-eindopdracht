package nl.novi.backendgarageservice.dto;

import jakarta.validation.constraints.NotBlank;

public class RoleDto {

    @NotBlank
    public String roleName;

}
