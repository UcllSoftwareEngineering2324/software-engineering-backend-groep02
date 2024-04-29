package be.ucll.se.groep02backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.car.repo.CarRepository;
import be.ucll.se.groep02backend.car.service.CarService;
import be.ucll.se.groep02backend.car.service.CarServiceException;
import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarService carService;

    private Car carOne = new Car("Ferrari", "488 GTB", "Super Car", "IT123", (short) 2, (short) 0, false, false);
    private Car carTwo = new Car("Audi", "A4", "Brake", "IT123", (short) 2, (short) 0, false, false);
    private Car carThree = new Car("BMW", "X5", "Brake", "IT123", (short) 2, (short) 0, false, false);
    private Car carFour = new Car("Lamborghini", "Aventador", "Super Car", "IT123", (short) 2, (short) 0, false, false);

    private User userOne = new User(1, "John", "Doe", "johndoe@example.com", "password", "1234567890", LocalDate.of(1990, 5, 15), "00.00.00-000.00", "1234567890", null, null, null);

    
    @Test
    public void givenNoCars_whenValidCarAdded_ThenCarIsAddedAndCarIsReturned() throws CarServiceException {
        // given
        when(carRepository.save(carOne)).thenReturn(carOne);

        // when
        Car added = carService.addCar(carOne, userOne);

        // then
        assertEquals(carOne.getBrand(), added.getBrand());
        assertEquals(carOne.getModel(), added.getModel());
        assertEquals(carOne.getType(), added.getType());
        assertEquals(carOne.getLicensePlate(), added.getLicensePlate());
        assertEquals(carOne.getNumberOfSeats(), added.getNumberOfSeats());
        assertEquals(carOne.getNumberOfChildSeats(), added.getNumberOfChildSeats());
        assertEquals(carOne.getFoldingRearSeat(), added.getFoldingRearSeat());
        assertEquals(carOne.getTowBar(), added.getTowBar());
    }

    @Test
    public void givenExistingCars_whenCarsAreAsked_ThenAllCarsAreReturned() throws CarServiceException {
        // given
        List<Car> listCars = new ArrayList<>();
        listCars.add(carOne);
        listCars.add(carTwo);
        listCars.add(carThree);
        listCars.add(carFour);
        when(carRepository.findAll()).thenReturn(listCars);

        // when
        List<Car> result = carService.getAllCars(userOne);

        // then
        assertEquals(4, result.size());
    }
    
}
