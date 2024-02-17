package be.ucll.se.groep02backend.car.repo;

import be.ucll.se.groep02backend.car.model.domain.Car;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long>{
    public List<Car> findAll();
    public Car findCarById(Long carId);
}
