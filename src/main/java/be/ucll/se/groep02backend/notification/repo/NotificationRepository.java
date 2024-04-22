package be.ucll.se.groep02backend.notification.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.se.groep02backend.notification.model.Notification;
import be.ucll.se.groep02backend.rent.model.domain.Rent;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
        public List<Notification> findAll();
        public Notification findNotificationById(Long id);
        public Notification findNotificationByRent(Rent rent);

}
