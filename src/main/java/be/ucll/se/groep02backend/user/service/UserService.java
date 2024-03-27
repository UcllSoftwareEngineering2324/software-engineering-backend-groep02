package be.ucll.se.groep02backend.user.service;

import java.util.List;
import java.util.Optional;

import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;
import be.ucll.se.groep02backend.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(User user) {
        if (user.getRole() == Role.ADMIN) {
            var users = repository.findAll();
            return users;
        } else {
            return List.of(user);
        }
    }

    public User createUser(User user) throws UserServiceException {
        Optional<User> foundOtherUser = repository.findByEmail(user.getEmail());
        if (foundOtherUser.isPresent()) {
            throw new UserServiceException("User", "User already exists");
        }
        // Now you can pass the User object to the save method
        return repository.save(user);

    }

    public User loginUser(String email, String password) {
        User user = repository.findByEmail(email).orElse(null);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

}
