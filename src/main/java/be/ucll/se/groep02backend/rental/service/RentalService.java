package be.ucll.se.groep02backend.rental.service;

import java.util.List;
import java.util.Optional;

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
        if(!rental.getStartDate().isBefore(rental.getEndDate())){
            throw new RentalServiceException("rental", "Start date must be before the end date");
        }
        Car car = carRepository.findCarById(carId);
        if (car == null) {
            throw new CarServiceException("car", "Car with id: " + carId + " does not exist");
        }
        rentalRepository.save(rental);
        car.setRental(rental);
        carRepository.save(car);
        return rental;
    }
}
