package be.ucll.se.groep02backend.rent.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.se.groep02backend.rent.model.domain.Rent;

public interface RentRepository extends JpaRepository<Rent, Long>{
    public List<Rent> findAll();
}
