package be.ucll.se.groep02backend.rental.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.car.model.domain.Car;
import be.ucll.se.groep02backend.car.repo.CarRepository;
import be.ucll.se.groep02backend.car.service.CarServiceException;
import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.rent.repo.RentRepository;
import be.ucll.se.groep02backend.rental.model.domain.Rental;
import be.ucll.se.groep02backend.rental.model.domain.SearchRentals;
import be.ucll.se.groep02backend.rental.repo.RentalRepository;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private CarRepository carRepository;

    public Map<String, List<Rental>> findAll() {
        List<Rental> rentals = rentalRepository.findAll();
        Map<String, List<Rental>> result = new HashMap<>();

        for (Rental rental: rentals) {
            Car car = carRepository.findCarByRentalsId(rental.id);

            String carKey = car.getBrand() + " " + car.getModel() + " " + car.getLicensePlate();

            result.putIfAbsent(carKey, new ArrayList<>());

            result.get(carKey).add(rental);
        }

        return result;
    }

    public Rental findRental(Long id) {
        return rentalRepository.findById(id).orElse(null);
    }

    public Rental addRental(Rental rental, Long carId) throws RentalServiceException, CarServiceException {
        LocalDate startDate = rental.getStartDate();
        LocalDate endDate = rental.getEndDate();
        if(startDate.isAfter(endDate)){
            throw new RentalServiceException("rental", "Start date must be before the end date");
        } else if (endDate.isBefore(startDate)) {
            throw new RentalServiceException("rental", "End date must be after the start date");
        } 

        Car car = carRepository.findCarById(carId);
        if (car == null) {
            throw new CarServiceException("car", "Car with id: " + carId + " does not exist");
        }

        rental.setCar(car);
        rentalRepository.save(rental);
        return rental;
    }

    public List<Rental> searchRentals(SearchRentals search) throws RentalServiceException, CarServiceException {
        List<Rental> foundRentals = rentalRepository.findRentalsByCriteria(search.getEmail(), search.getStartDate(), search.getEnddate(), search.getCity());
        List<Rental> finalRentals = new ArrayList<>();
 
        if (search.getBrand() != null) {
            if (!carRepository.existsByBrand(search.getBrand())) {
                return finalRentals;
            }
            List<Car> cars = carRepository.findAllCarsByBrand(search.getBrand());

            for (Rental rental: foundRentals) {
                for (Car car: cars) {
                    if (car.getRentals().contains(rental)) {
                        finalRentals.add(rental);
                    }
                }
            }
        } else {
            finalRentals.addAll(foundRentals);
        }

        if (finalRentals.isEmpty()) {
            throw new RentalServiceException("rental", "There are no rentals for given specifications!");
        } 

        return finalRentals;
        
    }

    public Rental deleteRental(Long id) throws RentalServiceException{
        Rental rental = rentalRepository.findRentalById(id);
        if(rental == null){
            throw new RentalServiceException("id", "Rental with given id does not exist");
        }
        Set<Rent> rents = rental.getRents();
        rental.removeAllRents();
        rentalRepository.save(rental);
        for(Rent rent: rents){
            rentRepository.delete(rent);
        }
        rentalRepository.delete(rental);
        return rental;
    }
}
