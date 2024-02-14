package be.ucll.se.groep02backend.greeting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.se.groep02backend.greeting.model.domain.Greeting;
import be.ucll.se.groep02backend.greeting.service.GreetingService;

@CrossOrigin(origins = "http://127.0.0.1:5501")
@RestController
@RequestMapping("/hello")
public class GreetingRestController {
    @Autowired
    private GreetingService greetingService;

    @GetMapping
    public List<Greeting> getGreeting() {
        return greetingService.findAll();
    }
}
