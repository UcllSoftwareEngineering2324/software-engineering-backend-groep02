package be.ucll.se.groep02backend.rental.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.rental.model.domain.Rental;
import be.ucll.se.groep02backend.rental.repo.RentalRepository;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Rental addRental(Rental rental) throws RentalServiceException {
        if(!rental.getStartDate().isBefore(rental.getEndDate())){
            throw new RentalServiceException("rental", "Start date must be before the end date");
        }
        return rentalRepository.save(rental);
    } 
}
