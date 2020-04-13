package one.digitalinnovation.personapi.repositories;

import one.digitalinnovation.personapi.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
