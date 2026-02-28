package assignment.sirenalert.repository;

import assignment.sirenalert.model.Siren;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SirenRepository extends JpaRepository<Siren, Integer> {
}
