// package be.ucll.se.groep02backend.Integration;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.Mockito.when;

// import org.aspectj.lang.annotation.Before;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;

// import be.ucll.se.groep02backend.auth.AuthenticationController;
// import be.ucll.se.groep02backend.car.controller.CarRestController;
// import be.ucll.se.groep02backend.car.model.Car;
// import be.ucll.se.groep02backend.car.service.CarService;
// import be.ucll.se.groep02backend.user.controller.UserController;
// import be.ucll.se.groep02backend.user.model.Role;
// import be.ucll.se.groep02backend.user.model.User;
// import be.ucll.se.groep02backend.user.model.UserInput;
// import be.ucll.se.groep02backend.user.service.UserService;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.TestPropertySource;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.RequestBuilder;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;

// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.hamcrest.Matchers.containsString;
// import static org.hamcrest.Matchers.hasSize;

// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.HashSet;
// import java.util.List;

// @AutoConfigureMockMvc
// @SpringBootTest
// @TestPropertySource(properties = {
//     "spring.datasource.url = jdbc:h2:file:./data/DB",
//     "spring.datasource.driverClassName=org.h2.Driver",
//     "spring.datasource.username=test",
//     "spring.datasource.password=",
//     "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
//     "spring.jpa.hibernate.ddl-auto=create-drop",
// })
// public class IntegrationTest {

//     private MockMvc mockMvc;

// 	@Autowired
// 	private UserController controller;

// 	@InjectMocks
// 	private CarRestController carRestController;
//     @MockBean
//     private CarService carService;

// 	@MockBean
// 	private UserService userService;

// 	@BeforeEach
//   	public void setUp(WebApplicationContext webApplicationContext) {
//     	this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//  	}

// 	// @Test
// 	// void contextLoads() throws Exception {
// 	// 	assertThat(controller).isNotNull();
// 	// }
	
// //     @Test
// //     void getAllCars() throws Exception {
// // 		User user = new User(          
// // 			1L,
// // 			"John",
// // 			"Doe",
// // 			"john.doe@example.com",
// // 			"password",
// // 			"+1234567890",
// // 			LocalDate.of(1990, 5, 15),
// // 			"YY.MM.DD-XXX.XX",
// // 			"1234567890",
// // 			null,
// // 			null, // Set<Car> cars,
// // 			null // Set<Rent> rents
// // 		);

// // 	Car car = new Car(
// //                 "Toyota",
// //                 "Camry",
// //                 "Sedan",
// //                 "ABC123",
// //                 (short) 5,
// //                 (short) 2,
// //                 true,
// //                 false
// // 	);
	
// // 	when(carService.getAllCars(user)).thenReturn(Collections.singletonList(car));
// // 	this.mockMvc.perform(get("/car"))
// // 		.andDo(print())
// // 		.andExpect(status().isBadRequest())
// // 		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
// // 	}

// // 	@Test
// //     void testGetAllCarsWithMultipleCars() throws Exception {
// // 		User user = new User(
// //             1L,
// //             "John",
// //             "Doe",
// //             "john.doe@example.com",
// //             "password",
// //             "+1234567890",
// //             LocalDate.of(1990, 5, 15),
// //             "YY.MM.DD-XXX.XX",
// //             "1234567890",
// //             null,
// //             null, // Set<Car> cars,
// //             null // Set<Rent> rents
// //     );

// //     Car car1 = new Car(
// //             "Toyota",
// //             "Camry",
// //             "Sedan",
// //             "ABC123",
// //             (short) 5,
// //             (short) 2,
// //             true,
// //             false
// //     );

// //     Car car2 = new Car(
// //             "Honda",
// //             "Accord",
// //             "Sedan",
// //             "XYZ456",
// //             (short) 5,
// //             (short) 2,
// //             true,
// //             false
// //     );

// //     when(carService.getAllCars(user)).thenReturn(Arrays.asList(car1, car2));

// //     this.mockMvc.perform(get("/car"))
// //             .andDo(print())
// //             .andExpect(status().isBadRequest());
// // }
// }
