package one.digitalinnovation.personapi.services;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.mapper.PersonMapper;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entities.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    public MessageResponseDTO create(PersonDTO personDTO) {
        Person person = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(person);

        MessageResponseDTO messageResponse = MessageResponseDTO.builder()
                .message("Person successfully created with ID " + savedPerson.getId())
                .build();

        return messageResponse;
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty()) {
            throw new PersonNotFoundException(id);
        }
        return personMapper.toDTO(person.get());
    }
}
