package be.ucll.se.groep02backend.greeting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.greeting.model.domain.Greeting;
import be.ucll.se.groep02backend.greeting.repo.GreetingRepository;

@Service
public class GreetingService {
    @Autowired
    private GreetingRepository greetingRepository;

    public GreetingService() {
    }

    public List<Greeting> findAll() {
        return greetingRepository.findAll();
    }
}
