package be.ucll.se.groep02backend.rental.repo;

import be.ucll.se.groep02backend.rental.model.domain.Rental;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long>{
    public List<Rental> findAll();
}
