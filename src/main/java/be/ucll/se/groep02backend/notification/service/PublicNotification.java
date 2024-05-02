package be.ucll.se.groep02backend.notification.service;

import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.rent.model.domain.PublicRent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PublicNotification {
    private long id;

    private PublicRent rent;

    private boolean viewedByMe;

}
