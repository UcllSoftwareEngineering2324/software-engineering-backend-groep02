package be.ucll.se.groep02backend.rental.model.domain;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.rent.model.domain.Rent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PublicRental {
    

    private long id;

    private Car car;

    @JsonBackReference
    private Set<Rent> rents;

    private LocalDate startDate;
    
    private LocalDate endDate;

    private String ownerEmail;
    
    private String street;

    private int streetNumber;
    

    private int postal;

    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "Base price is required")
    private float basePrice;

    @NotNull(message = "Price per Kilometer is required")
    private float pricePerKm;

    @NotNull(message = "Fuel penalty price is required")
    private float fuelPenaltyPrice;

    @NotNull(message = "Price per day is required")
    private float pricePerDay;


   
}