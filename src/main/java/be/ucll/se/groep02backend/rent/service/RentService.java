package be.ucll.se.groep02backend.rent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.notification.service.NotificationService;
import be.ucll.se.groep02backend.notification.service.NotificationServiceException;
import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.rent.model.domain.RentStatus;
import be.ucll.se.groep02backend.rent.repo.RentRepository;
import be.ucll.se.groep02backend.rental.model.domain.Rental;
import be.ucll.se.groep02backend.rental.repo.RentalRepository;
import be.ucll.se.groep02backend.rental.service.RentalServiceException;
import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private NotificationService notificationService;

    public List<Rent> getAllRents() throws RentServiceException {
        List<Rent> foundRents = rentRepository.findAll();
        if (foundRents.isEmpty()) {
            throw new RentServiceException("rent", "There are no rents");
        }
        return foundRents;
    }

    public List<Rent> getRentsByEmail(String email, User user) throws RentServiceException {
        if (user.getRoles().contains(Role.ADMIN)) {
            List<Rent> foundRents = rentRepository.findRentsByUserEmail(email);
            if (foundRents.size() == 0) {
                throw new RentServiceException("rent", "There are no rents for this email");
            }
            return foundRents;
        } else {
            List<Rent> foundRents = rentRepository.findRentsByUserEmail(user.getEmail());
            if (foundRents.size() == 0) {
                throw new RentServiceException("rent", "There are no rents for this email");
            }
            return foundRents;
        }
    }

    // public void test() {
    // rentRepository.getAllRentsByUserEmail()
    // rentRepository.getAllRentsByRentalByCarByUserEmail()

    // }

    public Rent checkinRent(Rent rent, Long rentalId, User user) throws RentServiceException, RentalServiceException {
        if (!user.getRoles().contains(Role.RENTER) && !user.getRoles().contains(Role.ADMIN)) {
            throw new RentServiceException("role", "User must be a renter or admin to rent a car");
        }
        Rental rental = rentalRepository.findRentalById(rentalId);
        if (rental == null) {
            throw new RentalServiceException("rent", "Rental does not exist with given id");
        }

        if (rent.getStartDate().isBefore(rental.getStartDate()) || rent.getEndDate().isAfter(rental.getEndDate())) {
            throw new RentServiceException("startDate", "Rent date is not in rental pêriod");
        }

        for (Rent checkedRent : rental.getRents()) {
            if (rent.getStartDate().isEqual(checkedRent.getStartDate())
                    ||
                    rent.getEndDate().isEqual(checkedRent.getEndDate())
                    ||
                    (rent.getStartDate().isAfter(checkedRent.getStartDate())
                            && rent.getStartDate().isBefore(checkedRent.getEndDate()))
                    ||
                    (rent.getEndDate().isAfter(checkedRent.getStartDate())
                            && rent.getEndDate().isBefore(checkedRent.getEndDate()))) {
                throw new RentServiceException("rent", "Cannot rent car");
            }
        }
        rent.setStatus(RentStatus.PENDING);
        rent.setUser(user);
        rent.setRental(rental);
        rentRepository.save(rent);
        notificationService.addNotification(rent);
        return rent;
    }

    public Rent checkoutRent(Long id, User user) throws RentServiceException, NotificationServiceException {
        if (!user.getRoles().contains(Role.RENTER) && !user.getRoles().contains(Role.ADMIN)) {
            throw new RentServiceException("role", "User must be a renter or admin to delete a rent");
        }
        Rent rent = rentRepository.findRentByIdAndUserEmail(id, user.getEmail());
        if (rent == null) {
            throw new RentServiceException("id",
            "Rent with given id does not exist or insufficient rights to delete given rent");
        }
        notificationService.deleteNotification(rent);
        Rental rental = rent.getRental();
        rental.removeRent(rent);
        rentalRepository.save(rental);
        rentRepository.delete(rent);
        return rent;
    }

    public Rent updateRentStatus(Long id, String status, User user) throws RentServiceException {
        if (!user.getRoles().contains(Role.OWNER) && !user.getRoles().contains(Role.ADMIN)) {
            throw new RentServiceException("role", "User must be an owner or admin to update a rent");
        }
        Rent rent = rentRepository.findRentById(id);
        if (rent == null) {
            throw new RentServiceException("id", "Rent with given id does not exist");
        }
        if (status.equals("confirm")) {
            rent.setStatus(RentStatus.CONFIRMED);
        } else if (status.equals("reject")) {
            rent.setStatus(RentStatus.REJECTED);
        } else {
            throw new RentServiceException("status", "Status is not valid");
        }
        rentRepository.save(rent);
        return rent;
    }
}
