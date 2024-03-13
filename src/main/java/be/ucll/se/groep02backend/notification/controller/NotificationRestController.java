package be.ucll.se.groep02backend.notification.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.se.groep02backend.notification.model.Notification;
import be.ucll.se.groep02backend.notification.service.NotificationService;
import be.ucll.se.groep02backend.notification.service.NotificationServiceException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/notification")
public class NotificationRestController {
    @Autowired
    private NotificationService notificationService;

    
    
    @GetMapping()
    public List<Notification> getMethodName() {
        return notificationService.findAll();
    }
    

    @PutMapping("/viewed/")
    public Notification viewedNotification(@PathVariable("notificationId") Long notificationId) throws NotificationServiceException {
        return notificationService.viewedNotification(notificationId);
    }

    
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ NotificationServiceException.class })
    public Map<String, String> handleUserServiceExceptions(NotificationServiceException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(), ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class })
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
