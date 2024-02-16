package be.ucll.se.groep02backend.rental.model.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

// JPA imports
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
// Validation imports
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Future;


@Entity
@Table(name= "rental")
public class Rental {
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    public long id;
    
    @NotNull(message="Start date is required")
    @Future(message="Start date is invalid, it has to be in the future")
    // Date format change because spring wont allow POST 
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    
    @NotNull(message="End date is required")
    @Future(message = "End date must be after the start date")
    // Date format change because spring wont allow POST
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    private String street;
    private int streetNumber;
    private int postal;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^\\w+@\\w+\\.\\w+$", message = "Email value is invalid, it has to be of the following format xxx@yyy.zzz")
    private String email;
    public Rental() {}

    public Rental(LocalDate startDate, LocalDate endDate, String street, int streetNumber, int postal, String city, String phoneNumber, String email) {
        setStartDate(startDate);
        setEndDate(endDate);
        setStreet(street);
        setStreetNumber(streetNumber);
        setPostal(postal);
        setCity(city);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    // Getters 
    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }
    
    public String getStreet() {
        return this.street;
    }
    
    public int getStreetNumber() {
        return this.streetNumber;
    }

    public int getPostal() {
        return this.postal;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }
   

    // Setters
    public void setStartDate(LocalDate starDate){
        this.startDate = starDate;
    }

    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }
    
    public void setStreet(String street){
        this.street = street;
    }

    public void setStreetNumber(int streetNumber){
        this.streetNumber = streetNumber;
    }

    public void setPostal(int postal){
        this.postal = postal;
    }
    
    public void setCity(String city){
        this.city = city;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}