package be.ucll.se.groep02backend.car.model.domain;

import java.util.HashSet;
import java.util.Set;

import be.ucll.se.groep02backend.rental.model.domain.Rental;
// JPA imports
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
// Validation imports
import jakarta.validation.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name= "car")
public class Car {
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    public long id;
    
    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private Set<Rental> rentals;
    
    // @ManyToOne
    // @JoinColumn(name = "rental_id")
    // @JsonBackReference
    // private Rental rental;

    
    @NotBlank(message="Brand is required")
    private String brand;

    public String model;

    @NotBlank(message="Type is required")
    private String type;
    
    @NotBlank(message="License plate is required")
    private String licensePlate;

    private short numberOfSeats;
    private short numberOfChildSeats;
    private boolean foldingRearSeat;
    private boolean towBar;
    
    public Car() {}

    public Car(String brand, String model, String type, String licensePlate, short numberOfSeats, short numberOfChildSeats, boolean foldingRearSeat, boolean towBar) {
        setBrand(brand);
        setModel(model);
        setType(type);
        setLicensePlate(licensePlate);
        setNumberOfSeats(numberOfSeats);
        setNumberOfChildSeats(numberOfChildSeats);
        setFoldingRearSeat(foldingRearSeat);
        setTowBar(towBar);
   }

   // Getters 
    public String getBrand() {
        return this.brand;
    }
    public String getModel() {
        return this.model;
    }
    public String getType() {
        return this.type;
    }
    public String getLicensePlate() {
        return this.licensePlate;
    }
    public short getNumberOfSeats() {
        return this.numberOfSeats;
    }
    public short getNumberOfChildSeats() {
        return this.numberOfChildSeats;
    }
    public boolean getFoldingRearSeat() {
        return this.foldingRearSeat;
    }
    public boolean getTowBar() {
        return this.towBar;
    }

    // Setters
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    public void setNumberOfSeats(short numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    public void setNumberOfChildSeats(short numberOfChildSeats) {
        this.numberOfChildSeats = numberOfChildSeats;
    }
    public void setFoldingRearSeat(boolean foldingRearSeat) {
        this.foldingRearSeat = foldingRearSeat;
    }
    public void setTowBar(boolean towBar) {
        this.towBar = towBar;
    }


    public Set<Rental> getRentals() {
        if (rentals == null) {
            rentals = new HashSet<>();
        }
        return rentals;
    }

    public void addRental(Rental rental) {
        this.getRentals().add(rental);
    }

    public void removeRental(Rental rental) {
        this.rentals.remove(rental);
    }

}
