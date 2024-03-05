package be.ucll.se.groep02backend.rental.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.se.groep02backend.car.model.domain.Car;
import be.ucll.se.groep02backend.car.service.CarService;
import be.ucll.se.groep02backend.car.service.CarServiceException;
import be.ucll.se.groep02backend.rental.model.domain.Rental;
import be.ucll.se.groep02backend.rental.model.domain.SearchRentals;
import be.ucll.se.groep02backend.rental.service.RentalService;
import be.ucll.se.groep02backend.rental.service.RentalServiceException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/rental")
public class RentalRestController {
    @Autowired
    private RentalService rentalService;

    @Autowired
    private CarService carService;

    @GetMapping
    public List<Rental> getRentals() {
        return rentalService.findAll();
    }

    @GetMapping("/get/")
    public Car getMethodName(@RequestParam("rentalId") Long rentalId) {
        Rental rental = rentalService.findRental(rentalId);
        return carService.findCarByRentalId(rental.id);
    }
    
    @PostMapping("/add/")
    public Rental addRental(@RequestBody @Valid Rental rental, @RequestParam(value = "carId", required = false) Long carId) throws RentalServiceException, CarServiceException {
        if (carId == null) {
        throw new RentalServiceException("rental", "carId must be provided in the URL");
        }

        return rentalService.addRental(rental, carId);
    }

    @GetMapping("/search/")
    public List<Rental> searchForRentals(@RequestBody SearchRentals search) throws RentalServiceException {
        return rentalService.searchRentals(search);
    }

    @DeleteMapping("/delete/")
    public Rental deleteRental(@RequestParam("rentalId") Long rentalId) throws RentalServiceException{
        return rentalService.deleteRental(rentalId);
    }

    // RentalServiceException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ RentalServiceException.class })
    public Map<String, String> handleUserServiceExceptions(RentalServiceException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(), ex.getMessage());
        return errors;
    }

    // CarServiceException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ CarServiceException.class })
    public Map<String, String> handleUserServiceExceptions(CarServiceException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(), ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class })
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
