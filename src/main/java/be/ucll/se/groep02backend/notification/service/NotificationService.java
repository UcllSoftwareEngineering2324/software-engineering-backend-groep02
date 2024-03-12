package be.ucll.se.groep02backend.notification.service;

import java.util.List;
import java.util.Objects;

import be.ucll.se.groep02backend.notification.model.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.notification.repo.NotificationRepository;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationService() {}

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification findNotificationById(Long id) {
        return notificationRepository.findNotificationById(id);
    }

    public Notification deleteNotification(Long id) throws NotificationServiceException{
        Notification notification = notificationRepository.findNotificationById(id);
        if(Objects.isNull(notification)){
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        notificationRepository.delete(notification);
        return notification;
    }

    public Notification completeNotification(Long id) throws NotificationServiceException {
        Notification notification = notificationRepository.findNotificationById(id);
        if(Objects.isNull(notification)){
            throw new NotificationServiceException("id", "Notification with given id does not exist.");
        }
        notification.setActive(false);
        return notificationRepository.save(notification);
    }

}
