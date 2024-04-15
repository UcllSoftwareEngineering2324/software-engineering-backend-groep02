package be.ucll.se.groep02backend.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.se.groep02backend.user.model.User;
import be.ucll.se.groep02backend.user.service.UserService;
import be.ucll.se.groep02backend.user.service.UserServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import be.ucll.se.groep02backend.config.ApplicationConfig;

@Tag(name = "User")
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users", description = "Get all users from the database.")
    @GetMapping()
    public List<User> getUsers() throws UserServiceException {

        return userService.getAllUsers(ApplicationConfig.getAuthenticatedUser());

    }

}
