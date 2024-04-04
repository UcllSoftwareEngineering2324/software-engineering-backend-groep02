package be.ucll.se.groep02backend.car.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.se.groep02backend.car.model.Car;

public interface CarRepository extends JpaRepository<Car, Long>{
    public List<Car> findAll();
    public Car findCarById(Long carId);
    public Car findCarByRentalsId(Long rentalId);
    public List<Car> findAllCarsByBrand(String brand);
    public boolean existsByBrand(String brand);
}
