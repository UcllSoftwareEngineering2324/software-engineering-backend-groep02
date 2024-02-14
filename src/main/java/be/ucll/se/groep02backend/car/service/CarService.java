package be.ucll.se.groep02backend.car.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.car.model.domain.Car;
import be.ucll.se.groep02backend.car.repo.CarRepository;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public CarService() {
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }
}
