package nl.novi.backendgarageservice.service;

import nl.novi.backendgarageservice.dto.RoleDto;
import nl.novi.backendgarageservice.model.Role;
import nl.novi.backendgarageservice.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RoleService {


    private final RoleRepository roleRepos;

    public RoleService(RoleRepository roleRepos) {
        this.roleRepos = roleRepos;
    }

    public ArrayList<RoleDto> getAllRoles() {
        ArrayList<RoleDto> roleDtos = new ArrayList<>();
        for (Role role : roleRepos.findAll()) {
            RoleDto roleDto = new RoleDto();
            roleDto.roleName = role.getRoleName();
            roleDtos.add(roleDto);
        }

        return roleDtos;

    }
}
