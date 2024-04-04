package be.ucll.se.groep02backend.car.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.car.service.CarService;
import be.ucll.se.groep02backend.car.service.CarServiceException;
import be.ucll.se.groep02backend.config.ApplicationConfig;
import be.ucll.se.groep02backend.user.service.UserServiceException;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Car")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/car")
public class CarRestController {
    @Autowired
    private CarService carService;

    @GetMapping
    public List<Car> getCars() {
        return carService.findAll();
    }

    @PostMapping("/add")
    public Car addCar(@RequestBody @Valid Car car) throws CarServiceException, UserServiceException {
        return carService.addCar(car,ApplicationConfig.getAuthenticatedUser());
    }

    @DeleteMapping("/delete/{id}")
    public Car deleteCar(@PathVariable("id") Long id) throws CarServiceException{
        return carService.deleteCar(id);
    }

}
