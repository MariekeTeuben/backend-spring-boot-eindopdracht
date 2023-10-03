package nl.novi.backendgarageservice.repository;

import nl.novi.backendgarageservice.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
