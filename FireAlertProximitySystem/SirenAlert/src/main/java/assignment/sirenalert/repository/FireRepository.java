package assignment.sirenalert.repository;

import assignment.sirenalert.model.Fire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireRepository extends JpaRepository<Fire, Integer> {
}
