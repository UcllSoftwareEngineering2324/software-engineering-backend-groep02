package be.ucll.se.groep02backend.rent.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.rent.service.RentService;
import be.ucll.se.groep02backend.rent.service.RentServiceException;
import be.ucll.se.groep02backend.rental.service.RentalServiceException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/rent")
public class RentRestController {
    @Autowired
    private RentService rentService;

    @GetMapping("/get/{email}")
    public List<Rent> getMethodName(@PathVariable String email) throws RentServiceException {
        return rentService.getRentsByEmail(email);
    }
    
    
    @PostMapping("/add/{rentalId}")
    public Rent addRent(@RequestBody @Valid Rent rent, @PathVariable("rentalId") Long rentalId)
            throws RentServiceException, RentalServiceException {
        return rentService.addRent(rent, rentalId);
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
    @ExceptionHandler({ RentServiceException.class })
    public Map<String, String> handleUserServiceExceptions(RentServiceException ex) {
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
