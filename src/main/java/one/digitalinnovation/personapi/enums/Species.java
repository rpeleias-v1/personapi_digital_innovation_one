package one.digitalinnovation.personapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Species {

    DOG("Dog"),
    CAT("Cat"),
    HAMSTER("Hamster"),
    REPTILE("Reptile");

    private final String description;
}