package nl.novi.backendgarageservice.repository;

import nl.novi.backendgarageservice.model.RegistrationCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface RegistrationCardRepository extends JpaRepository<RegistrationCard, String> {
    Optional<RegistrationCard> findByChassisNumber(String chassisNumber);
}
