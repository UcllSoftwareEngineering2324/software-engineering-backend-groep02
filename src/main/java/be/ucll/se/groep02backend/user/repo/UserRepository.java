package be.ucll.se.groep02backend.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.user.model.User;

<<<<<<< Updated upstream
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
=======
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
        public User findByRents(Rent rent);

    
}
>>>>>>> Stashed changes

    public User findByRents(Rent rent);
}
