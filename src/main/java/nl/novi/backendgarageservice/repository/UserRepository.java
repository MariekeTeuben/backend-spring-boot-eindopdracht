package nl.novi.backendgarageservice.repository;

import nl.novi.backendgarageservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
