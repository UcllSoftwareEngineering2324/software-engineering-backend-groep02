package be.ucll.se.groep02backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import be.ucll.se.groep02backend.auth.AuthenticationService;
import be.ucll.se.groep02backend.user.model.PublicUser;
import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;
import be.ucll.se.groep02backend.user.model.UserInput;
import be.ucll.se.groep02backend.user.service.UserService;

@Component
public class Seed implements ApplicationRunner {

    private AuthenticationService authenticationService;
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        // Add your startup logic here
        System.out.println("Application started. Running startup logic...");
        try {
            userSeed();
        } catch (Exception e) {
            System.out.println("Error seeding. =========> " + e.getMessage());
        }
    }

    @Autowired
    public void seed(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public void userSeed() throws Exception {
        // Create a UserInput object with necessary details
        UserInput user1 = new UserInput();
            user1.setEmail("admin@ucll.com");
            user1.setPassword("admin1234");
            user1.setFirstName("admin");
            user1.setLastName("bertels");
            user1.setPhoneNumber("0123456789");
            user1.setBirthDate(java.time.LocalDate.now());
            user1.setNationalRegisterNumber("00.00.00-000.00");
            user1.setLicenseNumber("0000000000");
            
            PublicUser response1 = authenticationService.register(user1);
            String token1 = response1.getToken();
            User user_1 = userService.getUserByEmail(user1.getEmail());
            userService.addRole("admin", user_1);
            


        UserInput user2 = new UserInput();
            user2.setEmail("user@ucll.com");
            user2.setPassword("user1234");
            user2.setFirstName("user");
            user2.setLastName("swennen");
            user2.setPhoneNumber("0123456789");
            user2.setBirthDate(java.time.LocalDate.now());
            user2.setNationalRegisterNumber("00.00.00-000.00");
            user2.setLicenseNumber("0000000000");

            PublicUser response2 = authenticationService.register(user2);
            String token2 = response2.getToken();

        System.out.println("<------------------------------------>");
        System.out.println(user1.getEmail() + ": " + token1);
        System.out.println("<------------------------------------>");
        System.out.println(user2.getEmail() + ": " + token2);
    }
}
