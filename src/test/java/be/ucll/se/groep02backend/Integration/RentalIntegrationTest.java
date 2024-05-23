package be.ucll.se.groep02backend.Integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RentalIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private WebTestClient client;

    private String token;
    private WebTestClient.ResponseSpec response;

    @Test
    public void getRentalsTest(){
        WebTestClient.ResponseSpec registerResponse = client.post()
        .uri("/register")
        .header("Content-Type", "application/json")
        .bodyValue("""
        {
            "email": "example@matteo.com",
            "password": "securePassword123",
            "firstName": "John",
            "lastName": "Doe",
            "isRenter": true,
            "isOwner": false,
            "phoneNumber": "1234567890",
            "birthDate": "1990-01-01",
            "nationalRegisterNumber": "90.01.01-123.45",
            "licenseNumber": "1234567890"
        }
        """)
        .exchange()
        .expectStatus()
        .isOk();

        String responseBody = registerResponse.returnResult(String.class).getResponseBody().blockFirst();
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            token = jsonNode.get("token").asText();
        } catch (Exception e) {
            System.out.println("Test");
        }

        response = client.get()
                .uri("/rental")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
