package be.ucll.se.groep02backend.config;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import be.ucll.se.groep02backend.auth.AuthenticationService;
import be.ucll.se.groep02backend.rental.service.RentalServiceException;
import be.ucll.se.groep02backend.user.model.PublicUser;
import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;
import be.ucll.se.groep02backend.user.model.UserInput;
import be.ucll.se.groep02backend.user.service.UserService;
import be.ucll.se.groep02backend.user.service.UserServiceException;

@Component
public class Seed implements ApplicationRunner {

    private AuthenticationService authenticationService;
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        // Add your startup logic here
        System.out.println("Application started. seeding data...");
        try {
            userSeed();
        } catch (UserServiceException e) {
            System.out.println("Error seeding. =========> " + e);
        } catch (Exception e) {
            System.out.println("Error seeding. =========> " + e);
        }
    }

    @Autowired
    public void seed(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public void userSeed() throws UserServiceException, Exception, MethodArgumentNotValidException{
        // -------------------> Admin <-------------------
        UserInput admin1 = new UserInput();
            admin1.setEmail("admin@ucll.com");
            admin1.setPassword("admin1234");
            admin1.setFirstName("admin");
            admin1.setLastName("swennen");
            admin1.setPhoneNumber("0123456789");
            admin1.setBirthDate(java.time.LocalDate.now().minusWeeks(3248));
            admin1.setNationalRegisterNumber("00.00.00-000.00");
            admin1.setLicenseNumber("0000000000");
            
            PublicUser admin1Response = authenticationService.register(admin1);
            String admin1Token = admin1Response.getToken();
            User admin_1 = userService.getUserByEmail(admin1.getEmail());
            userService.addRole("admin", admin_1);
            

        // -------------------> User 1 <-------------------
        UserInput user1 = new UserInput();
            user1.setEmail("user1@ucll.com");
            user1.setPassword("admin1234");
            user1.setFirstName("timo");
            user1.setLastName("swennen");
            user1.setPhoneNumber("0123456789");
            user1.setBirthDate(java.time.LocalDate.now().minusYears(21));
            user1.setNationalRegisterNumber("00.00.00-000.00");
            user1.setLicenseNumber("0000000000");

            PublicUser user1Response = authenticationService.register(user1);
            String user1Token = user1Response.getToken();
            User user_1 = userService.getUserByEmail(user1.getEmail());


        // -------------------> Owner 1 <-------------------
        UserInput owner1 = new UserInput();
            owner1.setEmail("owner1@ucll.com");
            owner1.setPassword("admin1234");
            owner1.setFirstName("matteo");
            owner1.setLastName("swennen");
            owner1.setPhoneNumber("0123456789");
            owner1.setBirthDate(java.time.LocalDate.now().minusDays(1201));
            owner1.setNationalRegisterNumber("12.53.48-811.32");
            owner1.setLicenseNumber("4584883362");

            PublicUser owner1Response = authenticationService.register(owner1);
            String owner1Token = owner1Response.getToken();
            User owner_1 = userService.getUserByEmail(owner1.getEmail());
            userService.addRole("owner", owner_1);


        // -------------------> Owner 2 <-------------------
        UserInput owner2 = new UserInput();
            owner2.setEmail("owner2@ucll.com");
            owner2.setPassword("admin1234"); // Keeping the same password as the previous user
            owner2.setFirstName("jos");
            owner2.setLastName("swennen");
            owner2.setPhoneNumber("0123456789");
            owner2.setBirthDate(java.time.LocalDate.of(1990, 5, 15));
            owner2.setNationalRegisterNumber("12.34.56-789.01"); // Assuming a fictional national register number
            owner2.setLicenseNumber("4584883362"); // Assuming a fictional license number
            
            PublicUser owner2Response = authenticationService.register(owner2);
            String owner2Token = owner2Response.getToken();
            User owner_2 = userService.getUserByEmail(owner2.getEmail());
            userService.addRole("owner", owner_2);


        // -------------------> Renter 1 <-------------------
        UserInput renter1 = new UserInput();
            renter1.setEmail("renter1@ucll.com");
            renter1.setPassword("admin1234");
            renter1.setFirstName("robin");
            renter1.setLastName("swennen");
            renter1.setPhoneNumber("0123456789");
            renter1.setBirthDate(java.time.LocalDate.now().minusDays(1201));
            renter1.setNationalRegisterNumber("12.53.48-811.32");
            renter1.setLicenseNumber("4584883362");

            PublicUser renter1Response = authenticationService.register(renter1);
            String renter1Token = renter1Response.getToken();
            User renter_1 = userService.getUserByEmail(renter1.getEmail());
            userService.addRole("renter", renter_1);

        // -------------------> Renter 2 <-------------------
        UserInput renter2 = new UserInput();
            renter2.setEmail("renter2@ucll.com");
            renter2.setPassword("admin1234");
            renter2.setFirstName("sarah");
            renter2.setLastName("smith");
            renter2.setPhoneNumber("9876543210");
            renter2.setBirthDate(java.time.LocalDate.now().minusDays(2000));
            renter2.setNationalRegisterNumber("23.45.67-890.12");
            renter2.setLicenseNumber("4584883362");

            PublicUser renter2Response = authenticationService.register(renter2);
            String renter2Token = renter2Response.getToken();
            User renter_2 = userService.getUserByEmail(renter2.getEmail());
            userService.addRole("renter", renter_2);

        // -------------------> Renter 3 <-------------------
        UserInput renter3 = new UserInput();
            renter3.setEmail("renter3@ucll.com");
            renter3.setPassword("admin1234");
            renter3.setFirstName("emma");
            renter3.setLastName("johnson");
            renter3.setPhoneNumber("1234567890");
            renter3.setBirthDate(java.time.LocalDate.now().minusDays(3000));
            renter3.setNationalRegisterNumber("34.56.78-901.23");
            renter3.setLicenseNumber("4584883362");

            PublicUser renter3Response = authenticationService.register(renter3);
            String renter3Token = renter3Response.getToken();
            User renter_3 = userService.getUserByEmail(renter3.getEmail());
            userService.addRole("renter", renter_3);

        // -------------------> Renter 4 <-------------------
        UserInput renter4 = new UserInput();
            renter4.setEmail("renter4@ucll.com");
            renter4.setPassword("admin1234");
            renter4.setFirstName("michael");
            renter4.setLastName("wilson");
            renter4.setPhoneNumber("4567890123");
            renter4.setBirthDate(java.time.LocalDate.now().minusDays(4000));
            renter4.setNationalRegisterNumber("45.67.89-012.34");
            renter4.setLicenseNumber("4584883362");

            PublicUser renter4Response = authenticationService.register(renter4);
            String renter4Token = renter4Response.getToken();
            User renter_4 = userService.getUserByEmail(renter4.getEmail());
            userService.addRole("renter", renter_4);

        // -------------------> Accountant 1 <-------------------
        UserInput accountant1 = new UserInput();
            accountant1.setEmail("accountant1@ucll.com");
            accountant1.setPassword("admin1234");
            accountant1.setFirstName("alice");
            accountant1.setLastName("brown");
            accountant1.setPhoneNumber("1112223333");
            accountant1.setBirthDate(java.time.LocalDate.now().minusDays(5000));
            accountant1.setNationalRegisterNumber("56.78.90-123.45");
            accountant1.setLicenseNumber("4584883362");

            PublicUser accountant1Response = authenticationService.register(accountant1);
            String accountant1Token = accountant1Response.getToken();
            User accountant_1 = userService.getUserByEmail(accountant1.getEmail());
            userService.addRole("accountant", accountant_1);

        // -------------------> Accountant 2 <-------------------
        UserInput accountant2 = new UserInput();
            accountant2.setEmail("accountant2@ucll.com");
            accountant2.setPassword("admin1234");
            accountant2.setFirstName("charlie");
            accountant2.setLastName("white");
            accountant2.setPhoneNumber("2223334444");
            accountant2.setBirthDate(java.time.LocalDate.now().minusDays(6000));
            accountant2.setNationalRegisterNumber("67.89.01-234.56");
            accountant2.setLicenseNumber("4584883362");

            PublicUser accountant2Response = authenticationService.register(accountant2);
            String accountant2Token = accountant2Response.getToken();
            User accountant_2 = userService.getUserByEmail(accountant2.getEmail());
            userService.addRole("accountant", accountant_2);


        System.out.println("<------------------------------------>");
        System.out.println(admin1.getEmail() + ": " + admin1Token);
        System.out.println("<------------------------------------>");
        System.out.println(user1.getEmail() + ": " + user1Token);
        System.out.println("<------------------------------------>");
        System.out.println(owner1.getEmail() + ": " + owner1Token);
        System.out.println("<------------------------------------>");
        System.out.println(renter1.getEmail() + ": " + renter1Token);
        System.out.println("<------------------------------------>");
        System.out.println(accountant1.getEmail() + ": " + renter1Token);



    }
}
