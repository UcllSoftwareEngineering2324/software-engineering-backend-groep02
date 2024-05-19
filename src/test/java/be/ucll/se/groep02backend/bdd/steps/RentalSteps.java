package be.ucll.se.groep02backend.bdd.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import be.ucll.se.groep02backend.Groep02BackendApplication;
import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.car.repo.CarRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = Groep02BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RentalSteps {

    @Autowired
    private WebTestClient client;

    @Autowired
    private CarRepository carRepository;

    private Car car;
    
    
    private WebTestClient.ResponseSpec response;

    @Before
    public void setup() {
        car = new Car("Ferrari", "488 GTB", "Super Car", "IT123", (short) 2, (short) 0, false, false);
    }

    @Given("some cars")
    public void some_cars() {
        carRepository.save(new Car("Ferrari", "488 GTB", "Super Car", "IT123", (short) 2, (short) 0, false, false));
        carRepository.save(new Car("Audi", "A4", "Brake", "IT123", (short) 2, (short) 0, false, false));
    }

    @When("I want to get all cars")
    public void i_want_to_get_all_cars() {
        response = client.get()
                .uri("/car")
                .exchange();
    }
    @Then("the data of all students is returned in json format")
    public void the_data_of_all_students_is_returned_in_json_format() {
        response.expectStatus()
                    .isOk();
    }
}
