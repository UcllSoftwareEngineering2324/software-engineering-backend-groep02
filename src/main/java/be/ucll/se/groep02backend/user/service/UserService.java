package be.ucll.se.groep02backend.user.service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import be.ucll.se.groep02backend.auth.RegisterRequest;
import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;
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

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User createUser(User user) throws UserServiceException {
        Optional<User> foundOtherUser = repository.findByEmail(user.getEmail());
        if (foundOtherUser.isPresent()) {
            throw new UserServiceException("User", "User already exists");
        }

        // Now you can pass the User object to the save method
        return repository.save(user);

    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public User loginUser(String email, String password) {
        User user = repository.findByEmail(email).orElse(null);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

}
