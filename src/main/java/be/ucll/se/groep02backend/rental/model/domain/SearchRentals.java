package be.ucll.se.groep02backend.rental.model.domain;

import java.time.LocalDate;

public class SearchRentals {

    private String email;
    private LocalDate startDate;
    private LocalDate endDate;
    private String brand;
    private String city;

    public String getEmail() {
        return this.email;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEnddate() {
        return this.endDate;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getCity() {
        return this.city;
    }

    public void SetEmail(String email) {
        this.email = email;
    }

    public void setStartDate(LocalDate starDate){
        this.startDate = starDate;
    }

    public void setEnddate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
