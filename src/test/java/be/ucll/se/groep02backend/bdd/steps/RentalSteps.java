package be.ucll.se.groep02backend.bdd.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import be.ucll.se.groep02backend.Groep02BackendApplication;
import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.car.repo.CarRepository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


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

    @Autowired
    private ObjectMapper objectMapper;

    private Car car;
    private String token;
    private WebTestClient.ResponseSpec response;

    @Before
    public void setup() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        car = new Car("Ferrari", "488 GTB", "Super Car", "IT123", (short) 2, (short) 0, false, false);
    }

    @Given("some cars")
    public void some_cars() {
        carRepository.save(new Car("Ferrari", "488 GTB", "Super Car", "IT123", (short) 2, (short) 0, false, false));
        carRepository.save(new Car("Audi", "A4", "Brake", "IT123", (short) 2, (short) 0, false, false));
    }

    @When("I want to get all cars")
    public void i_want_to_get_all_cars() {
        // response = client.post()
        // .uri("/register")
        // .bodyValue("""
        // {
        //     "email": "example@matteo.com",
        //     "password": "securePassword123",
        //     "firstName": "John",
        //     "lastName": "Doe",
        //     "isRenter": true,
        //     "isOwner": false,
        //     "phoneNumber": "1234567890",
        //     "birthDate": "1990-01-01",
        //     "nationalRegisterNumber": "90.01.01-123.45",
        //     "licenseNumber": "1234567890"
        // }
        // """)
        // .exchange();

        
        // String responseBody = registerResponse.returnResult(String.class).getResponseBody().blockFirst();
        // try {
        //     JsonNode jsonNode = objectMapper.readTree(responseBody);
        //     token = jsonNode.get("token").asText();
        //     System.out.println("Extracted token: " + token); // Debugging output to verify token extraction
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

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
