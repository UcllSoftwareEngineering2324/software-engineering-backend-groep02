package be.ucll.se.groep02backend.rent.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.se.groep02backend.config.ApplicationConfig;
import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.rent.service.RentService;
import be.ucll.se.groep02backend.rent.service.RentServiceException;
import be.ucll.se.groep02backend.rental.service.RentalServiceException;
import be.ucll.se.groep02backend.user.service.UserServiceException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;




@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Rent")
@RestController
@RequestMapping("/rent")
public class RentRestController {
    @Autowired
    private RentService rentService;

    // Returns a dict with car as string and a rent object
    @GetMapping
    public List<Rent> getAllRents() throws RentServiceException {
        return rentService.getAllRents();
    }
    
    @GetMapping("/get/")
    public List<Rent> getMethodName(@RequestParam String email) throws RentServiceException {
        return rentService.getRentsByEmail(email);
    }
    
    
    @PostMapping("/add/{rentalId}")
    public Rent addRent(@RequestBody @Valid Rent rent, @PathVariable("rentalId") Long rentalId)
            throws RentServiceException, RentalServiceException, UserServiceException {
        return rentService.addRent(rent, rentalId, ApplicationConfig.getAuthenticatedUser());
    }

    @DeleteMapping("/delete/")
    public Rent deleteRent(@RequestParam("rentId") Long rentId) throws RentServiceException{
        return rentService.deleteRent(rentId);
    }

    @PutMapping("/status/{status}/{id}")
    public Rent updateRentStatus(@PathVariable Long id, @PathVariable String status) throws RentServiceException{
        return rentService.updateRentStatus(id, status);
    }
    
}
