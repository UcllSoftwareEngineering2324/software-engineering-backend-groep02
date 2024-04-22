package be.ucll.se.groep02backend.notification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import be.ucll.se.groep02backend.notification.model.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.notification.repo.NotificationRepository;
import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.rent.model.domain.RentStatus;
import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    

    public NotificationService() {}

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public List<Notification> getMyNotifications(User user) {
        List<Notification> notifications = new ArrayList<>();
        if (user.getRoles().contains(Role.OWNER)) {
            // // Retrieve cars owned by the owner
            // List<Car> cars = carRepository.findByOwner(user);
            // for (Car car : cars) {
            //     // Retrieve the rental associated with the car
            //     Rental rental = rentalRepository.findByCar(car);
            //     if (rental != null) {
            //         // Retrieve the rent associated with the rental
            //         Rent rent = rental.getRent();
            //         if (rent != null) {
            //             // Retrieve the notification associated with the rent
            //             Notification notification = rent.getNotification();
            //             if (notification != null && !notification.isRenterViewed()) {
            //                 notifications.add(notification);
            //             }
            //         }
            //     }
            // }
            notifications.addAll(notificationRepository.findNotificationsByRentRentalCarUserAndOwnerViewed(user, false));
            return notifications;
        }
        if (user.getRoles().contains(Role.RENTER)) {
            // Logic to retrieve notifications for renter role
            notifications.addAll(notificationRepository.findNotificationsByRentUserAndRenterViewedAndRentStatusNot(user, false, RentStatus.PENDING));
            
        }
        return notifications;
    }
    

    public Notification addNotification(Rent rent) {
        Notification notification = new Notification(
            false,
            false,
            rent
        );
        return notificationRepository.save(notification);
    }

    public Notification findNotificationById(Long id) throws NotificationServiceException {
        Notification notification = notificationRepository.findNotificationById(id);
        if(Objects.isNull(notification)){
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        return notification;
    }

    public Notification deleteNotification(Long id) throws NotificationServiceException{
        Notification notification = notificationRepository.findNotificationById(id);
        if(Objects.isNull(notification)){
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        notificationRepository.delete(notification);
        return notification;
    }
    public Notification deleteNotification(Rent rent) throws NotificationServiceException{
        Notification notification = notificationRepository.findNotificationByRent(rent);
        if(Objects.isNull(notification)){
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        notificationRepository.delete(notification);
        return notification;
    }

    

    public Notification viewedNotification(Long id) throws NotificationServiceException {
        Notification notification = notificationRepository.findNotificationById(id);
        if(Objects.isNull(notification)){
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        if (notification.getReceiverViewed() == false && notification.getOwnerViewed() == true){
            notification.setReceiverViewed(true);
        }
        else{
            throw new NotificationServiceException("id", "Notification can't be viewed by receiver if owner hasn't viewed it yet.");
        }
        if (notification.getOwnerViewed() == false){
            notification.setOwnerViewed(true);
        }
        return notificationRepository.save(notification);
    }

    // not yet in use ---------> use when user impelmentation is done
    public Notification ownerViewed(Long id) throws NotificationServiceException {
        Notification notification = notificationRepository.findNotificationById(id);
        if(Objects.isNull(notification)){
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        notification.setOwnerViewed(true);
        return notificationRepository.save(notification);
    }
    public Notification receiverViewed (Long id) throws NotificationServiceException {
        Notification notification = notificationRepository.findNotificationById(id);
        if(Objects.isNull(notification)){
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        if(notification.getOwnerViewed() == false){
            throw new NotificationServiceException("id", "Notification can't be viewed by receiver if owner hasn't viewed it yet.");
        }
        notification.setReceiverViewed(true);
        return notificationRepository.save(notification);
    }

}
