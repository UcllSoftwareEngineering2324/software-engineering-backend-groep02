package be.ucll.se.groep02backend.car.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.se.groep02backend.car.model.domain.Car;
import be.ucll.se.groep02backend.car.service.CarService;

@CrossOrigin(origins = "http://127.0.0.1:5501")
@RestController
@RequestMapping("/hello")
public class CarRestController {
    @Autowired
    private CarService carService;

    @GetMapping
    public List<Car> getCar() {
        return carService.findAll();
    }
}
