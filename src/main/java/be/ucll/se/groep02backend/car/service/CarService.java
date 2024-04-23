package be.ucll.se.groep02backend.car.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.car.repo.CarRepository;
import be.ucll.se.groep02backend.rental.model.domain.Rental;
import be.ucll.se.groep02backend.rental.repo.RentalRepository;
import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentalRepository rentalRepository;

    public CarService() {
    }

    public List<Car> getAllCars(User user) throws CarServiceException{
        if (user.getRoles().contains(Role.ADMIN)) {
            return carRepository.findAll();
        } else if (user.getRoles().contains(Role.OWNER)) {
            return carRepository.findAllCarsByUser(user);
        } else {
            throw new CarServiceException("role", "User is not an owner.");
        }
    }


    public Car addCar(Car car, User user) throws CarServiceException {
        if (user.getRoles().contains(Role.OWNER) || user.getRoles().contains(Role.ADMIN)) {
            car.setUser(user);
            return carRepository.save(car);
        } else {
            throw new CarServiceException("role", "User is not an owner.");
        }
    }

    public Car findCarByRentalId(Long rentalId) {
        return carRepository.findCarByRentalsId(rentalId);
    }

    public Car deleteCar(Long id, User user) throws CarServiceException {
        if (!user.getRoles().contains(Role.OWNER) && !user.getRoles().contains(Role.ADMIN)) {
            throw new CarServiceException("role", "User is not an owner.");
        } else {
            if (user.getRoles().contains(Role.ADMIN)) {
                Car car = carRepository.findCarById(id);
                Set<Rental> rentals = car.getRentals();
                for (Rental rental : rentals) {
                    car.removeRental(rental);
                    rentalRepository.delete(rental);
                }
                carRepository.delete(car);
                return car;
            } else {

                if (user.getCars().contains(carRepository.findCarById(id))) {
                    Car car = carRepository.findCarById(id);
                    Set<Rental> rentals = car.getRentals();
                    for (Rental rental : rentals) {
                        car.removeRental(rental);
                        rentalRepository.delete(rental);
                    }
                    carRepository.delete(car);
                    return car;
                } else {
                    throw new CarServiceException("user", "Car is not owned by user.");
                }
            }
        }
    }

    public List<Car> getAllCarsByUser(User user) throws CarServiceException {
        if (user.getRoles().contains(Role.OWNER) || user.getRoles().contains(Role.ADMIN)) {
            return carRepository.findAllCarsByUser(user);
        } else {
            throw new CarServiceException("role", "User is not an owner.");
        }

    }
}
