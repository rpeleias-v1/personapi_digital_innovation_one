package one.digitalinnovation.personapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MainController {

    @GetMapping
    public String getWelcome(){
        return "Welcome to my API!<br />Bem-vindo a minha API!";
    }
}
