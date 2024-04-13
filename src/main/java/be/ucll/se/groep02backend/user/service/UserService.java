package be.ucll.se.groep02backend.user.service;

import java.util.List;
import java.util.Optional;

import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;
import be.ucll.se.groep02backend.user.model.UserInput;
import be.ucll.se.groep02backend.user.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(User user) {
        if (user.getAuthorities().contains(Role.ADMIN)) {
            var users = repository.findAll();
            return users;
        } else {
            return List.of(user);
        }
    }

    public User createUser(UserInput userInput) throws UserServiceException {
        Optional<User> foundOtherUser = repository.findByEmail(userInput.getEmail());
        if (foundOtherUser.isPresent()) {
            throw new UserServiceException("User", "User already exists");
        }
        User newUser  = User.builder()
                .firstName(userInput.getFirstName())
                .lastName(userInput.getLastName())
                .email(userInput.getEmail())
                .password(passwordEncoder.encode(userInput.getPassword()))
                .phoneNumber(userInput.getPhoneNumber())
                .birthDate(userInput.getBirthDate())
                .nationalRegisterNumber(userInput.getNationalRegisterNumber())
                .licenseNumber(userInput.getLicenseNumber())
                .build();
        newUser.addAuthority(Role.USER);
        System.out.println("User: " + newUser);
        // Now you can pass the User object to the save method
        return repository.save(newUser);

    }

    public User loginUser(String email, String password) {
        User user = repository.findByEmail(email).orElse(null);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User makeUserAdmin(User user) {
        if (user.getAuthorities().contains(Role.ADMIN)) {
            return user;
        }
        user.addAuthority(Role.ADMIN);
        repository.save(user);
        return user;
    }

}
