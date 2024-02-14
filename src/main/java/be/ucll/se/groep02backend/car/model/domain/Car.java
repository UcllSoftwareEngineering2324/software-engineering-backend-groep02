package be.ucll.se.groep02backend.car.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name= "car")
public class Car {
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    public long id;
    private String text;

    public Car() {}

    public Car(String text) {
        this.text=text;
    }

    public String getText() {
        return this.text;
    }
}