package be.ucll.se.groep02backend.rent.model.domain;

import java.time.LocalDate;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import be.ucll.se.groep02backend.car.model.domain.Car;
import be.ucll.se.groep02backend.rental.model.domain.Rental;
// JPA imports
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// Validation imports
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
// import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;


@Entity
@Table(name = "rent")
public class Rent {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public long id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    @JsonBackReference
    private Rental rental;

    @NotNull(message="Start date is required")
    @FutureOrPresent(message="Start date is invalid, it has to be in the future")
    // Date format change because spring wont allow POST 
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    
    @NotNull(message="End date is required")
    @Future(message = "End date is invalid, it has to be in the future")
    // Date format change because spring wont allow POST
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    
    @NotBlank(message = "Email is required")
    // @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$", message = "Email value is invalid, it has to be of the following format xxx@yyy.zzz")
    @Email(message = "Email value is invalid, it has to be of the following format xxx@yyy.zzz")
    private String email;

    @NotBlank(message = "Identification number of national register is required")
    private String nationalRegisterNumber;

    @NotNull(message = "Birthdate is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "Driving license number is required")
    private String licenseNumber;

    public Rent() {
    }

    public Rent(LocalDate startDate, LocalDate endDate, String phoneNumber, String email, String nationalRegisterNumber, LocalDate birthDate, String licenseNumber) {
        setStartDate(startDate);
        setEndDate(endDate);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setNationalRegisterNumber(nationalRegisterNumber);
        setBirthDate(birthDate);
        setLicenseNumber(licenseNumber);
    }

    // Getters 
    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNationalRegisterNumber() {
        return this.nationalRegisterNumber;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public String getLicenseNumber() {
        return this.licenseNumber;
    }

    // Setters
    public void setStartDate(LocalDate starDate){
        this.startDate = starDate;
    }

    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNationalRegisterNumber(String nationalRegisterNumber) {
        this.nationalRegisterNumber = nationalRegisterNumber;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Rental getRental(){
        return rental;
    }

    public void setRental(Rental rental){
        this.rental = rental;
        rental.addRent(this);
    }

    public void removeRental(){
        this.rental = null;
        rental.removeRent(this);
    }
}