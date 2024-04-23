package be.ucll.se.groep02backend.rent.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.se.groep02backend.rent.model.domain.Rent;

public interface RentRepository extends JpaRepository<Rent, Long>{
    public List<Rent> findAll();
    public Rent findRentByIdAndUserEmail(Long id, String email);
    public Rent findRentById(Long id);
    public List<Rent> findRentsByUserEmail(String email);
}
