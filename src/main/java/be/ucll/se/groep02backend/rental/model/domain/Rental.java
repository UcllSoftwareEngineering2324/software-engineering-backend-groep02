package be.ucll.se.groep02backend.rental.model.domain;

import java.util.Date;

// JPA imports
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
// Validation imports
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "rental")
public class Rental {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public long id;

    @NotNull(message = "Start date is required")
    private Date startDate;

    @NotBlank(message = "")
    private Date endDate;

    private String street;

    private int streetNumber;
    private int postal;
    private String city;
    private String phoneNumber;

    @Email
    private String email;

    public Rental() {
    }

    public Rental(Date startDate, Date endDate, String street, int streetNumber, int postal, String city,
            String phoneNumber) {
        setStartDate(startDate);
        setEndDate(endDate);
        setStreet(street);
        setStreetNumber(streetNumber);
        setPostal(postal);
        setCity(city);
        setPhoneNumber(phoneNumber);
    }

    // Getters
    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEnDate() {
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
    public void setStartDate(Date starDate) {
        this.startDate = starDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setPostal(int postal) {
        this.postal = postal;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}