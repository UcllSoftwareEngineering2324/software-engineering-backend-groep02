package be.ucll.se.groep02backend.notification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@Table(name = "notification")   
public class Notification {
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    public long id;
    
    @NotBlank(message="Title is required")
    private String title;

}
