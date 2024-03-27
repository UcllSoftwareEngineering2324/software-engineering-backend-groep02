package be.ucll.se.groep02backend.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.se.groep02backend.user.model.User;
import be.ucll.se.groep02backend.user.service.UserService;
import be.ucll.se.groep02backend.user.service.UserServiceException;
import be.ucll.se.groep02backend.config.ApplicationConfig;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping()
    public List<User> getUsers() throws UserServiceException {
        return userService.getAllUsers(ApplicationConfig.getAuthenticatedUser());
        
    }
}
