package be.ucll.se.groep02backend.rent.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.bill.model.Bill;
import be.ucll.se.groep02backend.bill.repo.BillRepository;
import be.ucll.se.groep02backend.car.model.Car;
import be.ucll.se.groep02backend.car.repo.CarRepository;
import be.ucll.se.groep02backend.notification.service.NotificationService;
import be.ucll.se.groep02backend.notification.service.NotificationServiceException;
import be.ucll.se.groep02backend.rent.model.domain.PublicRent;
import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.rent.model.domain.RentStatus;
import be.ucll.se.groep02backend.rent.repo.RentRepository;
import be.ucll.se.groep02backend.rental.model.domain.Rental;
import be.ucll.se.groep02backend.rental.repo.RentalRepository;
import be.ucll.se.groep02backend.rental.service.RentalServiceException;
import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;
import be.ucll.se.groep02backend.user.repo.UserRepository;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    public List<PublicRent> getAllRents() throws RentServiceException {
        List<PublicRent> foundRents = new ArrayList<>();
        List<Rent> rents = rentRepository.findAll();  
        if (rents.isEmpty()) {
            throw new RentServiceException("rent", "There are no rents");
        }
        for (Rent rent : rents) {
            String ownerEmail = rentRepository.findEmailByRentalCarUser(rent).getEmail();
            if (ownerEmail == null) {
                throw new RentServiceException("email", "Owner email not found");
            }
            foundRents.add(new PublicRent(rent, ownerEmail));
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

    // Check in rent
    public Rent checkIn(Long id, User user) throws RentServiceException {
        Rent rent = rentRepository.findRentById(id);

        if (rent == null) {
            throw new RentServiceException("rent", "Rent with given id does not exist");
        } else {
            if (rent.getStatus().equals(RentStatus.PENDING)) {
                rent.setCheckInDate(LocalDate.now());
                rent.setCheckInStatus(true);
                rentRepository.save(rent);
            } else {
                throw new RentServiceException("rent", "Cannot check in rent");
            }
    
            return rent;
        }
    }

    // Check out rent
    public Rent checkOut(Long id, User user, double distance, int fuelValue) throws RentServiceException {
        Rent rent = rentRepository.findRentById(id);

        if (rent == null) {
            throw new RentServiceException("rent", "Rent with given id does not exist");
        } 
        
        if (rent.getCheckInStatus() == true) {
            rent.setCheckOutDate(LocalDate.now());
            
            Long days = ChronoUnit.DAYS.between(rent.getCheckInDate(), rent.getCheckOutDate());
            double total = 0;

            if (fuelValue < 90) {
                total = rent.getRental().getBasePrice() + (rent.getRental().getPricePerKm() * distance) + (rent.getRental().getPricePerDay() * days) + rent.getRental().getFuelPenaltyPrice();
            } else {
                total = rent.getRental().getBasePrice() + (rent.getRental().getPricePerKm() * distance) + (rent.getRental().getPricePerDay() * days);
            }

            Bill bill = new Bill(rent.getUser().getEmail(), rent.getCar().getUser().getEmail(), rent.getRental().getCar().getBrand(), rent.getRental().getCar().getModel(), rent.getRental().getCar().getLicensePlate(), distance, days, fuelValue, total);

            rentRepository.save(rent);
            billRepository.save(bill);

        } else {
            throw new RentServiceException("rent", "Cannot check out rent");
        }

        return rent;
    }
}
