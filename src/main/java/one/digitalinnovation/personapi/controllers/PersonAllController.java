package one.digitalinnovation.personapi.controllers;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people/all")
public class PersonAllController {
    private PersonService personService;

    @Autowired
    public PersonAllController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDTO> listAll() {
        return personService.listAll();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById() throws PersonNotFoundException {
        personService.deleteAll();
    }
}
