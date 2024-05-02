package be.ucll.se.groep02backend.notification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.ucll.se.groep02backend.notification.model.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.notification.repo.NotificationRepository;
import be.ucll.se.groep02backend.rent.model.domain.PublicRent;
import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.rent.model.domain.RentStatus;
import be.ucll.se.groep02backend.rent.repo.RentRepository;
import be.ucll.se.groep02backend.rent.service.RentServiceException;
import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;
import be.ucll.se.groep02backend.user.repo.UserRepository;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private UserRepository userRepository;

    public NotificationService() {
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public List<PublicNotification> getOwnerNotifications(User user) throws NotificationServiceException {
        List<PublicNotification> publicNotifications = new ArrayList<>();
        if (user.getRoles().contains(Role.OWNER)) {
            List<Notification> notifications = notificationRepository.findNotificationsByRentRentalCarUser(user);
            for (Notification notification : notifications) {
                PublicNotification publicNotification = new PublicNotification(
                        notification.getId(),
                        new PublicRent(notification.getRent(), user.getEmail()),
                        notification.getOwnerViewed());
                if (notification.getRent().getStatus() == RentStatus.PENDING) {
                    publicNotifications.add(publicNotification);
                }
            }
            return publicNotifications;
        } else {
            throw new NotificationServiceException("role",
                    "User must be an owner to view the notifications in getOwnerNotifications. (renters should use the other endpoint)");
        }
    }

    // public List<PublicNotification> getAdminNotifications(User user) throws NotificationServiceException {
    //     List<PublicNotification> publicNotifications = new ArrayList<>();
    //     if (user.getRoles().contains(Role.OWNER)) {
    //         List<Notification> notifications = notificationRepository.findNotificationsByRentRentalCarUser(user);
    //         for (Notification notification : notifications) {
    //             PublicNotification publicNotification = new PublicNotification(
    //                     notification.getId(),
    //                     new PublicRent(notification.getRent(), user.getEmail()),
    //                     notification.getOwnerViewed());
    //             publicNotifications.add(publicNotification);
    //         }
    //         return publicNotifications;
    //     } else {
    //         throw new NotificationServiceException("role",
    //                 "User must be an owner to view the notifications in getOwnerNotifications. (renters should use the other endpoint)");
    //     }
    // }

    public Notification addNotification(Rent rent) {
        Notification notification = new Notification(
                false,
                false,
                rent);
        return notificationRepository.save(notification);
    }

    public Notification deleteNotification(Rent rent) throws NotificationServiceException {
        Notification notification = notificationRepository.findNotificationByRent(rent);
        if (Objects.isNull(notification)) {
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        notificationRepository.delete(notification);
        return notification;
    }

    // not yet in use ---------> use when user impelmentation is done
    public String ownerViewed(Long id, User user) throws NotificationServiceException {
        if (!user.getRoles().contains(Role.OWNER)) {
            throw new NotificationServiceException("role",
                    "User must be an owner to view the notification in ownerViewed.");
        }
        Notification notification = notificationRepository.findNotificationById(id);
        if (Objects.isNull(notification)) {
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        User owner = rentRepository.findByRentalCarUser(notification.getRent());
        if (!user.getEmail().equals(owner.getEmail())) {
            throw new NotificationServiceException("role",
                    "User must be the owner of the car to view the notification in onwnerviewed.");
        }
        if (notification.getOwnerViewed() == true) {
            throw new NotificationServiceException("viewed", "Notification has already been viewed by owner.");
        }
        notification.setOwnerViewed(true);
        notificationRepository.save(notification);
        return "Rent viewed by owner";
    }

    public String renterViewed(Long id, User user) throws NotificationServiceException {
        if (!user.getRoles().contains(Role.RENTER)) {
            throw new NotificationServiceException("role",
                    "User must be a renter to view the notification in receiverViewed.");
        }
        Notification notification = notificationRepository.findNotificationById(id);
        if (Objects.isNull(notification)) {
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        User renter = userRepository.findByRents(notification.getRent());
        if (!user.getEmail().equals(renter.getEmail())) {
            throw new NotificationServiceException("role",
                    "User must be the renter to view the renterViewed in notification.");
        }
        if (notification.getRent().getStatus() == RentStatus.PENDING) {
            throw new NotificationServiceException("rentstatus",
                    "rent status is still pending, you can't view the notification yet.");
        }
        if (notification.getReceiverViewed() == true) {
            throw new NotificationServiceException("viewed", "Notification has already been viewed by renter.");
        }
        notification.setReceiverViewed(true);
        notificationRepository.save(notification);
        return "Rent viewed by renter";
    }

}
