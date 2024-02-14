package be.ucll.se.groep02backend.greeting.repo;

import be.ucll.se.groep02backend.greeting.model.domain.Greeting;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greeting, Long>{
    public List<Greeting> findAll();
}
