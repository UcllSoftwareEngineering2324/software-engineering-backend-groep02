package be.ucll.se.groep02backend.rental.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.car.model.domain.Car;
import be.ucll.se.groep02backend.car.repo.CarRepository;
import be.ucll.se.groep02backend.car.service.CarServiceException;
import be.ucll.se.groep02backend.rental.model.domain.Rental;
import be.ucll.se.groep02backend.rental.repo.RentalRepository;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CarRepository carRepository;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
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
}
