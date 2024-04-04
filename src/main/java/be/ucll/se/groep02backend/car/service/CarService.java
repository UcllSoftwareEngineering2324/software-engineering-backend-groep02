package be.ucll.se.groep02backend.car.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.car.repo.CarRepository;
import be.ucll.se.groep02backend.rental.model.domain.Rental;
import be.ucll.se.groep02backend.rental.repo.RentalRepository;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentalRepository rentalRepository;

    public CarService() {
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car addCar(Car car) throws CarServiceException {
        return carRepository.save(car);
    } 

    public Car findCarByRentalId(Long rentalId) {
        return carRepository.findCarByRentalsId(rentalId);
    }

    public Car deleteCar(Long id) throws CarServiceException{
        Car car = carRepository.findCarById(id);
        if(Objects.isNull(car)){
            throw new CarServiceException("id", "Car with given id does not exist.");
        }
        Set<Rental> rentals = car.getRentals();
        for(Rental rental : rentals){
            car.removeRental(rental);
            rentalRepository.delete(rental);
        }
        carRepository.delete(car);
        return car;
    }
}
