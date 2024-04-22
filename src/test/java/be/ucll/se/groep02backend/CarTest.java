package be.ucll.se.groep02backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.user.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class CarTest {

    private Car car1;


    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    // valid values
    private static final String validBrand = "Ferrari";
    private static final String validModel = "488 GTB";
    private static final String validType = "Supercar";
    private static final String validLicensePlate = "IT123";
    private static final short validNumberOfSeats = 2;
    private static final short validNumberOfChildSeats = 0;
    private static final boolean validFoldingRearSeat = false;
    private static final boolean validTowBar = false;

    // invalid values
    private static final String invalidBrand = "";
    private static final String invalidModel = "";
    private static final String invalidType = "";
    private static final String invalidLicensePlate = "";
    private static final short invalidNumberOfSeats = -1;
    private static final short invalidNumberOfChildSeats = -1;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void setUp() {
        car1 = new Car("Ferrari", "488 GTB", "Supercar", "IT123", (short) 2, (short) 0, false, false);
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void givenValidValues_whenCreatingCar_thenCarIsCreatedWithBrand() {
        assertNotNull(car1);
        assertEquals(validBrand, car1.getBrand());
        Set<ConstraintViolation<Car>> violations = validator.validate(car1);
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void givenValidModel_whenCreatingCar_thenCarIsCreatedWithModel() {
        assertNotNull(car1);
        assertEquals(validModel, car1.getModel());
        Set<ConstraintViolation<Car>> violations = validator.validate(car1);
        assertTrue(violations.isEmpty());
    }
}
