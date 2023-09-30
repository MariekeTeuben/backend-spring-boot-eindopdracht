package nl.novi.backendgarageservice.repository;

import nl.novi.backendgarageservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
