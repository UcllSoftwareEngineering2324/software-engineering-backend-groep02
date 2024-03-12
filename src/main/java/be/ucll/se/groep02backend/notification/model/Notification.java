package be.ucll.se.groep02backend.notification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "notification")
public class Notification {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "content is required")
    private String content;

    @Valid
    private Boolean isActive;

    @Valid
    private ActionType action;

    public Notification() {
    }

    public Notification(String title, String content, Boolean isActive, ActionType action) {
        setTitle(title);
        setContent(content);
        setActive(isActive);
        setAction(action);
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Boolean getActive() {
        return this.isActive;
    }

    public ActionType getAction() {
        return this.action;
    }

    public void setTitle(String newtitle) {
        this.title = newtitle;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    public void setActive(Boolean newIsActive) {
        this.isActive = newIsActive;
    }

    public void setAction(ActionType newAction) {
        this.action = newAction;
    }


    public enum ActionType {
        None, Confirm_rent;
    }


    

}
