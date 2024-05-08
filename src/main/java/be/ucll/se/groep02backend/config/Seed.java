package be.ucll.se.groep02backend.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import be.ucll.se.groep02backend.auth.AuthenticationService;
import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.car.service.CarService;
import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.rent.service.RentService;
import be.ucll.se.groep02backend.rental.model.domain.Rental;
import be.ucll.se.groep02backend.rental.service.RentalService;
import be.ucll.se.groep02backend.user.model.PublicUser;
import be.ucll.se.groep02backend.user.model.User;
import be.ucll.se.groep02backend.user.model.UserInput;
import be.ucll.se.groep02backend.user.service.UserService;
import be.ucll.se.groep02backend.user.service.UserServiceException;

@Component
public class Seed implements ApplicationRunner {

    private AuthenticationService authenticationService;
    private UserService userService;
    private CarService carService;
    private RentalService rentalService;
    private RentService rentService;
    private EmailService emailService;


    @Override
    public void run(ApplicationArguments args) {
        // Add your startup logic here
        System.out.println("Application started. seeding data...");
        try {
            seedData();
        } catch (UserServiceException e) {
            System.out.println("Error seeding. =========> " + e);
        } catch (Exception e) {
            System.out.println("Error seeding. =========> " + e);
        }
    }

    @Autowired
    public void seed(AuthenticationService authenticationService, UserService userService, CarService carService, RentalService rentalService, RentService rentService, EmailService emailService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.carService = carService;
        this.rentalService = rentalService;
        this.rentService = rentService;
        this.emailService = emailService;
    }

    public void seedData() throws UserServiceException, Exception, MethodArgumentNotValidException {
        LocalDate today = LocalDate.now();
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
        admin1.setIsAdmin(true);
        admin1.setIsAccountant(false);
        admin1.setIsOwner(false);
        admin1.setIsRenter(false);

        PublicUser admin1Response = authenticationService.register(admin1);
        String admin1Token = admin1Response.getToken();
        User admin_1 = userService.getUserByEmail(admin1.getEmail());

        Car car1 = new Car("Ferrari", "488 GTB", "Supercar", "IT123", (short) 2, (short) 0, false, false);
        Car car2 = new Car("Volkswagen", "Golf", "Hatchback", "DE123", (short) 5, (short) 2, true, false);
        carService.addCar(car1, admin_1);
        carService.addCar(car2, admin_1);

        Rental rental1 = new Rental(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), "Misery street", 13, 3000, "Leuven",(float) 100,(float) 1,(float) 20,(float) 80);
        Rental rental2 = new Rental(LocalDate.now().plusDays(4), LocalDate.now().plusDays(9), "Misery street", 14, 3000, "Leuven",(float)100, (float)1, (float)21, (float)80);

        rentalService.addRental(rental1, car1.id, admin_1);
        rentalService.addRental(rental2, car2.id, admin_1);


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
        owner1.setIsOwner(true);
        owner1.setIsAccountant(false);
        owner1.setIsAdmin(false);
        owner1.setIsRenter(false);

        PublicUser owner1Response = authenticationService.register(owner1);
        String owner1Token = owner1Response.getToken();
        User owner_1 = userService.getUserByEmail(owner1.getEmail());
        Car car3 = new Car("BMW", "3 Series", "Sedan", "DE456", (short) 5, (short) 2, true, false);
        Car car4 = new Car("Mercedes-Benz", "C-Class", "Sedan", "DE789", (short) 5, (short) 2, true, false);
        Car car5 = new Car("Audi", "A4", "Sedan", "DE012", (short) 5, (short) 2, true, false);
        Car car16 = new Car("Citroen", "C4", "Sedan", "FR012", (short) 5, (short) 2, true, false);
        Car car17 = new Car("Dacia", "Duster", "SUV", "FR345", (short) 5, (short) 2, true, true);
        carService.addCar(car3, owner_1);
        carService.addCar(car4, owner_1);
        carService.addCar(car5, owner_1);
        carService.addCar(car16, owner_1);
        carService.addCar(car17, owner_1);

        Rental rental3 = new Rental(LocalDate.now().plusDays(3), LocalDate.now().plusDays(5), "Happy street", 22, 2000, "Brussels", (float) 120, (float) 8, (float) 25, (float) 100);
        Rental rental4 = new Rental(LocalDate.now().plusDays(2), LocalDate.now().plusDays(7), "Sunny street", 8, 1000, "Antwerp", (float) 90, (float) 7, (float) 22, (float)90);
        Rental rental5 = new Rental(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), "Rainy street", 5, 500, "Ghent", (float) 80, (float) 5, (float) 20, (float) 80);
        Rental rental6 = new Rental(LocalDate.now().plusDays(4), LocalDate.now().plusDays(9), "Cloudy street", 10, 800, "Bruges", (float) 100, (float) 6, (float) 23, (float) 90);
        Rental rental7 = new Rental(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), "Snowy street", 15, 1200, "Hasselt", (float) 110, (float) 9, (float) 24, (float) 100);
        rentalService.addRental(rental3, car3.id, owner_1);
        rentalService.addRental(rental4, car4.id, owner_1);
        rentalService.addRental(rental5, car5.id, owner_1);
        rentalService.addRental(rental6, car16.id, owner_1);
        rentalService.addRental(rental7, car17.id, owner_1);

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
        owner2.setIsOwner(true);
        owner2.setIsAccountant(false);
        owner2.setIsAdmin(false);
        owner2.setIsRenter(false);

        PublicUser owner2Response = authenticationService.register(owner2);
        String owner2Token = owner2Response.getToken();
        User owner_2 = userService.getUserByEmail(owner2.getEmail());
        Car car6 = new Car("Jaguar", "F-Type", "Sports Car", "UK456", (short) 2, (short) 0, false, false);
        Car car7 = new Car("Maserati", "GranTurismo", "Sports Car", "IT987", (short) 2, (short) 0, false, false);
        Car car8 = new Car("Lotus", "Evora", "Sports Car", "UK234", (short) 2, (short) 0, false, false);
        Car car9 = new Car("Bugatti", "Chiron", "Hypercar", "FR123", (short) 2, (short) 0, false, false);
        Car car10 = new Car("Rolls-Royce", "Phantom", "Luxury Car", "UK678", (short) 2, (short) 0, false, false);
        Car car11 = new Car("Volvo", "XC60", "SUV", "SE123", (short) 5, (short) 2, true, true);
        Car car12 = new Car("Land Rover", "Range Rover Evoque", "SUV", "UK789", (short) 5, (short) 2, true, true);
        Car car13 = new Car("Mini", "Cooper", "Hatchback", "UK012", (short) 4, (short) 0, true, false);
        Car car14 = new Car("Renault", "Megane", "Hatchback", "FR456", (short) 5, (short) 2, true, false);
        Car car15 = new Car("Peugeot", "308", "Hatchback", "FR789", (short) 5, (short) 2, true, false);
        carService.addCar(car6, owner_2);
        carService.addCar(car7, owner_2);
        carService.addCar(car8, owner_2);
        carService.addCar(car9, owner_2);
        carService.addCar(car10, owner_2);
        carService.addCar(car11, owner_2);
        carService.addCar(car12, owner_2);
        carService.addCar(car13, owner_2);
        carService.addCar(car14, owner_2);
        carService.addCar(car15, owner_2);

        Rental rental8 = new Rental(LocalDate.now().plusDays(3), LocalDate.now().plusDays(5), "Happy street", 22, 2000, "Brussels", (float) 120, (float) 8, (float) 25, (float) 100);
        Rental rental9 = new Rental(LocalDate.now().plusDays(2), LocalDate.now().plusDays(7), "Sunny street", 8, 1000, "Antwerp", (float) 90, (float) 7, (float) 22, (float) 90);
        Rental rental10 = new Rental(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), "Rainy street", 5, 500, "Ghent", (float) 80, (float) 5, (float) 20, (float) 80);
        Rental rental11 = new Rental(LocalDate.now().plusDays(4), LocalDate.now().plusDays(9), "Cloudy street", 10, 800, "Bruges", (float) 100, (float) 6, (float) 23, (float) 90);
        rentalService.addRental(rental8, car6.id, owner_2);
        rentalService.addRental(rental9, car7.id, owner_2);
        rentalService.addRental(rental10, car8.id, owner_2);
        rentalService.addRental(rental11, car9.id, owner_2);
        
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
        renter1.setIsRenter(true); // Ensure isRenter is explicitly set to true
        renter1.setIsAccountant(false);
        renter1.setIsOwner(false);
        renter1.setIsAdmin(false);
        
        PublicUser renter1Response = authenticationService.register(renter1);
        String renter1Token = renter1Response.getToken();
        User renter_1 = userService.getUserByEmail(renter1.getEmail());
        Rent rent1 = new Rent(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        Rent rent2 = new Rent(LocalDate.now().plusDays(2), LocalDate.now().plusDays(4));
        Rent rent3 = new Rent(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        Rent rent4 = new Rent(LocalDate.now().plusDays(3), LocalDate.now().plusDays(5));
        rentService.checkinRent(rent1, rental1.id, renter_1);
        rentService.checkinRent(rent2, rental9.id, renter_1);
        rentService.checkinRent(rent3, rental10.id, renter_1);
        rentService.checkinRent(rent4, rental3.id, renter_1);


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
        renter2.setIsRenter(true); // Ensure isRenter is explicitly set to true
        renter2.setIsAccountant(false);
        renter2.setIsOwner(false);
        renter2.setIsAdmin(false);

        PublicUser renter2Response = authenticationService.register(renter2);
        String renter2Token = renter2Response.getToken();
        User renter_2 = userService.getUserByEmail(renter2.getEmail());

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
        renter3.setIsRenter(true); // Ensure isRenter is explicitly set to true
        renter3.setIsAccountant(false);
        renter3.setIsOwner(false);
        renter3.setIsAdmin(false);

        PublicUser renter3Response = authenticationService.register(renter3);
        String renter3Token = renter3Response.getToken();
        User renter_3 = userService.getUserByEmail(renter3.getEmail());

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
        renter4.setIsRenter(true);
        renter4.setIsAccountant(false);
        renter4.setIsOwner(false);
        renter4.setIsAdmin(false);

        PublicUser renter4Response = authenticationService.register(renter4);
        String renter4Token = renter4Response.getToken();
        User renter_4 = userService.getUserByEmail(renter4.getEmail());

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
        accountant1.setIsAccountant(true);
        accountant1.setIsRenter(false);
        accountant1.setIsOwner(false);
        accountant1.setIsAdmin(false);

        PublicUser accountant1Response = authenticationService.register(accountant1);
        String accountant1Token = accountant1Response.getToken();
        User accountant_1 = userService.getUserByEmail(accountant1.getEmail());

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
        accountant2.setIsAccountant(true);
        accountant2.setIsRenter(false);
        accountant2.setIsOwner(false);
        accountant2.setIsAdmin(false);
        

        PublicUser accountant2Response = authenticationService.register(accountant2);
        String accountant2Token = accountant2Response.getToken();
        User accountant_2 = userService.getUserByEmail(accountant2.getEmail());

        System.out.println("<------------------------------------>");
        System.out.println(admin1.getEmail() + ": " + admin1Token);
        System.out.println("<------------------------------------>");
        System.out.println(owner1.getEmail() + ": " + owner1Token);
        System.out.println("<------------------------------------>");
        System.out.println(renter1.getEmail() + ": " + renter1Token);
        System.out.println("<------------------------------------>");
        System.out.println(accountant1.getEmail() + ": " + accountant1Token);


        // -------------------> Email <-------------------

        emailService.sendEmail("probablyspambin@gmail.com", "Welcome to Car Rental UCLL", "<!DOCTYPE html>\r\n" + //
                        "\r\n" + //
                        "<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\r\n" + //
                        "<head>\r\n" + //
                        "<title></title>\r\n" + //
                        "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\r\n" + //
                        "<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]-->\r\n" + //
                        "<style>\r\n" + //
                        "\t\t* {\r\n" + //
                        "\t\t\tbox-sizing: border-box;\r\n" + //
                        "\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\tbody {\r\n" + //
                        "\t\t\tmargin: 0;\r\n" + //
                        "\t\t\tpadding: 0;\r\n" + //
                        "\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\ta[x-apple-data-detectors] {\r\n" + //
                        "\t\t\tcolor: inherit !important;\r\n" + //
                        "\t\t\ttext-decoration: inherit !important;\r\n" + //
                        "\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t#MessageViewBody a {\r\n" + //
                        "\t\t\tcolor: inherit;\r\n" + //
                        "\t\t\ttext-decoration: none;\r\n" + //
                        "\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\tp {\r\n" + //
                        "\t\t\tline-height: inherit\r\n" + //
                        "\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t.desktop_hide,\r\n" + //
                        "\t\t.desktop_hide table {\r\n" + //
                        "\t\t\tmso-hide: all;\r\n" + //
                        "\t\t\tdisplay: none;\r\n" + //
                        "\t\t\tmax-height: 0px;\r\n" + //
                        "\t\t\toverflow: hidden;\r\n" + //
                        "\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t.image_block img+div {\r\n" + //
                        "\t\t\tdisplay: none;\r\n" + //
                        "\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t@media (max-width:620px) {\r\n" + //
                        "\r\n" + //
                        "\t\t\t.desktop_hide table.icons-inner,\r\n" + //
                        "\t\t\t.social_block.desktop_hide .social-table {\r\n" + //
                        "\t\t\t\tdisplay: inline-block !important;\r\n" + //
                        "\t\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t\t.icons-inner {\r\n" + //
                        "\t\t\t\ttext-align: center;\r\n" + //
                        "\t\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t\t.icons-inner td {\r\n" + //
                        "\t\t\t\tmargin: 0 auto;\r\n" + //
                        "\t\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t\t.mobile_hide {\r\n" + //
                        "\t\t\t\tdisplay: none;\r\n" + //
                        "\t\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t\t.row-content {\r\n" + //
                        "\t\t\t\twidth: 100% !important;\r\n" + //
                        "\t\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t\t.stack .column {\r\n" + //
                        "\t\t\t\twidth: 100%;\r\n" + //
                        "\t\t\t\tdisplay: block;\r\n" + //
                        "\t\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t\t.mobile_hide {\r\n" + //
                        "\t\t\t\tmin-height: 0;\r\n" + //
                        "\t\t\t\tmax-height: 0;\r\n" + //
                        "\t\t\t\tmax-width: 0;\r\n" + //
                        "\t\t\t\toverflow: hidden;\r\n" + //
                        "\t\t\t\tfont-size: 0px;\r\n" + //
                        "\t\t\t}\r\n" + //
                        "\r\n" + //
                        "\t\t\t.desktop_hide,\r\n" + //
                        "\t\t\t.desktop_hide table {\r\n" + //
                        "\t\t\t\tdisplay: table !important;\r\n" + //
                        "\t\t\t\tmax-height: none !important;\r\n" + //
                        "\t\t\t}\r\n" + //
                        "\t\t}\r\n" + //
                        "\t</style>\r\n" + //
                        "</head>\r\n" + //
                        "<body style=\"background-color: #d9dffa; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\r\n" + //
                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #d9dffa;\" width=\"100%\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td>\r\n" + //
                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #cfd6f4;\" width=\"100%\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td>\r\n" + //
                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px; margin: 0 auto;\" width=\"600\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-top: 20px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\r\n" + //
                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\" style=\"width:100%;\">\r\n" + //
                        "<div align=\"center\" class=\"alignment\" style=\"line-height:10px\">\r\n" + //
                        "<div style=\"max-width: 600px;\"><img alt=\"Card Header with Border and Shadow Animated\" height=\"auto\" src=\"images/animated_header.gif\" style=\"display: block; height: auto; border: 0; width: 100%;\" title=\"Card Header with Border and Shadow Animated\" width=\"600\"/></div>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table>\r\n" + //
                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #d9dffa; background-image: url('images/body_background_2.png'); background-position: top center; background-repeat: repeat;\" width=\"100%\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td>\r\n" + //
                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px; margin: 0 auto;\" width=\"600\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 15px; padding-left: 50px; padding-right: 50px; padding-top: 15px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\r\n" + //
                        "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\">\r\n" + //
                        "<div style=\"color:#506bec;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;font-size:38px;line-height:120%;text-align:left;mso-line-height-alt:45.6px;\">\r\n" + //
                        "<p style=\"margin: 0; word-break: break-word;\"><strong><span>Forgot your password?</span></strong></p>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\">\r\n" + //
                        "<div style=\"color:#40507a;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;font-size:16px;line-height:120%;text-align:left;mso-line-height-alt:19.2px;\">\r\n" + //
                        "<p style=\"margin: 0; word-break: break-word;\"><span>Hey, we received a request to reset your password.</span></p>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\">\r\n" + //
                        "<div style=\"color:#40507a;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;font-size:16px;line-height:120%;text-align:left;mso-line-height-alt:19.2px;\">\r\n" + //
                        "<p style=\"margin: 0; word-break: break-word;\"><span>Let’s get you a new one!</span></p>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"button_block block-4\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\" style=\"padding-bottom:20px;padding-left:10px;padding-right:10px;padding-top:20px;text-align:left;\">\r\n" + //
                        "<div align=\"left\" class=\"alignment\"><!--[if mso]>\r\n" + //
                        "<v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"http://www.example.com/\" style=\"height:48px;width:212px;v-text-anchor:middle;\" arcsize=\"34%\" stroke=\"false\" fillcolor=\"#506bec\">\r\n" + //
                        "<w:anchorlock/>\r\n" + //
                        "<v:textbox inset=\"5px,0px,0px,0px\">\r\n" + //
                        "<center style=\"color:#ffffff; font-family:Arial, sans-serif; font-size:15px\">\r\n" + //
                        "<![endif]--><a href=\"http://www.example.com/\" style=\"text-decoration:none;display:inline-block;color:#ffffff;background-color:#506bec;border-radius:16px;width:auto;border-top:0px solid TRANSPARENT;font-weight:undefined;border-right:0px solid TRANSPARENT;border-bottom:0px solid TRANSPARENT;border-left:0px solid TRANSPARENT;padding-top:8px;padding-bottom:8px;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;font-size:15px;text-align:center;mso-border-alt:none;word-break:keep-all;\" target=\"_blank\"><span style=\"padding-left:25px;padding-right:20px;font-size:15px;display:inline-block;letter-spacing:normal;\"><span style=\"word-break:break-word;\"><span data-mce-style=\"\" style=\"line-height: 30px;\"><strong>RESET MY PASSWORD</strong></span></span></span></a><!--[if mso]></center></v:textbox></v:roundrect><![endif]--></div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-5\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\">\r\n" + //
                        "<div style=\"color:#40507a;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;font-size:14px;line-height:120%;text-align:left;mso-line-height-alt:16.8px;\">\r\n" + //
                        "<p style=\"margin: 0; word-break: break-word;\"><span>Having trouble? <a href=\"http://www.example.com/\" rel=\"noopener\" style=\"text-decoration: none; color: #40507a;\" target=\"_blank\" title=\"@socialaccount\"><strong>@socialaccount</strong></a></span></p>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-6\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\">\r\n" + //
                        "<div style=\"color:#40507a;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;font-size:14px;line-height:120%;text-align:left;mso-line-height-alt:16.8px;\">\r\n" + //
                        "<p style=\"margin: 0; word-break: break-word;\">Didn’t request a password reset? You can ignore this message.</p>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table>\r\n" + //
                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td>\r\n" + //
                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px; margin: 0 auto;\" width=\"600\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\r\n" + //
                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\" style=\"width:100%;\">\r\n" + //
                        "<div align=\"center\" class=\"alignment\" style=\"line-height:10px\">\r\n" + //
                        "<div style=\"max-width: 600px;\"><img alt=\"Card Bottom with Border and Shadow Image\" height=\"auto\" src=\"images/bottom_img.png\" style=\"display: block; height: auto; border: 0; width: 100%;\" title=\"Card Bottom with Border and Shadow Image\" width=\"600\"/></div>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table>\r\n" + //
                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-4\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td>\r\n" + //
                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px; margin: 0 auto;\" width=\"600\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 20px; padding-left: 10px; padding-right: 10px; padding-top: 10px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\r\n" + //
                        "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"image_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\">\r\n" + //
                        "<div align=\"center\" class=\"alignment\" style=\"line-height:10px\">\r\n" + //
                        "<div style=\"max-width: 145px;\"><a href=\"http://www.example.com/\" style=\"outline:none\" tabindex=\"-1\" target=\"_blank\"><img alt=\"Your Logo\" height=\"auto\" src=\"images/logo.png\" style=\"display: block; height: auto; border: 0; width: 100%;\" title=\"Your Logo\" width=\"145\"/></a></div>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"social_block block-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\">\r\n" + //
                        "<div align=\"center\" class=\"alignment\">\r\n" + //
                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"social-table\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block;\" width=\"72px\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td style=\"padding:0 2px 0 2px;\"><a href=\"https://www.instagram.com\" target=\"_blank\"><img alt=\"Instagram\" height=\"auto\" src=\"images/instagram2x.png\" style=\"display: block; height: auto; border: 0;\" title=\"instagram\" width=\"32\"/></a></td>\r\n" + //
                        "<td style=\"padding:0 2px 0 2px;\"><a href=\"https://www.twitter.com\" target=\"_blank\"><img alt=\"Twitter\" height=\"auto\" src=\"images/twitter2x.png\" style=\"display: block; height: auto; border: 0;\" title=\"twitter\" width=\"32\"/></a></td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\">\r\n" + //
                        "<div style=\"color:#97a2da;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;font-size:14px;line-height:120%;text-align:center;mso-line-height-alt:16.8px;\">\r\n" + //
                        "<p style=\"margin: 0; word-break: break-word;\">+(123) 456–7890</p>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-4\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\">\r\n" + //
                        "<div style=\"color:#97a2da;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;font-size:14px;line-height:120%;text-align:center;mso-line-height-alt:16.8px;\">\r\n" + //
                        "<p style=\"margin: 0; word-break: break-word;\">This link will expire in the next 24 hours.<br/>Please feel free to contact us at email@yourbrand.com. </p>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block block-5\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\">\r\n" + //
                        "<div style=\"color:#97a2da;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;font-size:12px;line-height:120%;text-align:center;mso-line-height-alt:14.399999999999999px;\">\r\n" + //
                        "<p style=\"margin: 0; word-break: break-word;\"><span>Copyright© 2021 Your Brand.</span></p>\r\n" + //
                        "<p style=\"margin: 0; word-break: break-word;\"><span><a href=\"http://www.example.com/\" rel=\"noopener\" style=\"text-decoration: underline; color: #97a2da;\" target=\"_blank\" title=\"Unsubscribe \">Unsubscribe</a> | <a href=\"http://www.example.com/\" rel=\"noopener\" style=\"text-decoration: underline; color: #97a2da;\" target=\"_blank\" title=\"Manage your preferences\">Manage your preferences</a> | <a href=\"http://www.example.com/\" rel=\"noopener\" style=\"text-decoration: underline; color: #97a2da;\" target=\"_blank\" title=\"Privacy Policy\">Privacy Policy</a></span></p>\r\n" + //
                        "</div>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table>\r\n" + //
                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-5\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff;\" width=\"100%\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td>\r\n" + //
                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; background-color: #ffffff; width: 600px; margin: 0 auto;\" width=\"600\">\r\n" + //
                        "<tbody>\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\r\n" + //
                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"icons_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; text-align: center;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"pad\" style=\"vertical-align: middle; color: #1e0e4b; font-family: 'Inter', sans-serif; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">\r\n" + //
                        "<table cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\r\n" + //
                        "<tr>\r\n" + //
                        "<td class=\"alignment\" style=\"vertical-align: middle; text-align: center;\"><!--[if vml]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->\r\n" + //
                        "<!--[if !vml]><!-->\r\n" + //
                        "<table cellpadding=\"0\" cellspacing=\"0\" class=\"icons-inner\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block; margin-right: -4px; padding-left: 0px; padding-right: 0px;\"><!--<![endif]-->\r\n" + //
                        "<tr>\r\n" + //
                        "<td style=\"vertical-align: middle; text-align: center; padding-top: 5px; padding-bottom: 5px; padding-left: 5px; padding-right: 6px;\"><a href=\"http://designedwithbeefree.com/\" style=\"text-decoration: none;\" target=\"_blank\"><img align=\"center\" alt=\"Beefree Logo\" class=\"icon\" height=\"auto\" src=\"images/Beefree-logo.png\" style=\"display: block; height: auto; margin: 0 auto; border: 0;\" width=\"34\"/></a></td>\r\n" + //
                        "<td style=\"font-family: 'Inter', sans-serif; font-size: 15px; font-weight: undefined; color: #1e0e4b; vertical-align: middle; letter-spacing: undefined; text-align: center;\"><a href=\"http://designedwithbeefree.com/\" style=\"color: #1e0e4b; text-decoration: none;\" target=\"_blank\">Designed with Beefree</a></td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table>\r\n" + //
                        "</td>\r\n" + //
                        "</tr>\r\n" + //
                        "</tbody>\r\n" + //
                        "</table><!-- End -->\r\n" + //
                        "</body>\r\n" + //
                        "</html>");

    }
}
