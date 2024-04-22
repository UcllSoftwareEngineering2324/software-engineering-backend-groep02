package be.ucll.se.groep02backend.notification.service;

import java.util.List;
import java.util.Objects;

import be.ucll.se.groep02backend.notification.model.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.notification.repo.NotificationRepository;
import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.user.model.User;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationService() {}

    public List<Notification> findAll() {
        return notificationRepository.findAll();
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
