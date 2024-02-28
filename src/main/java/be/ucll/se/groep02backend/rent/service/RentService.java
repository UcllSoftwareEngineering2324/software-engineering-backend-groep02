package be.ucll.se.groep02backend.rent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.rent.model.domain.Rent;
import be.ucll.se.groep02backend.rent.repo.RentRepository;
import be.ucll.se.groep02backend.rental.model.domain.Rental;
import be.ucll.se.groep02backend.rental.repo.RentalRepository;
import be.ucll.se.groep02backend.rental.service.RentalServiceException;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private RentalRepository rentalRepository;

    public Rent addRent(Rent rent, Long rentalId) throws RentServiceException, RentalServiceException{
        Rental rental = rentalRepository.findRentalById(rentalId);
        if(rental == null){
            throw new RentalServiceException("rent", "Rental does not exist with given id");
        }

        if(rent.getStartDate().isBefore(rental.getStartDate()) || rent.getEndDate().isAfter(rental.getEndDate())){
            throw new RentServiceException("startDate", "Rent date is not in rental pêriod");
        }

        for(Rent checkedRent : rental.getRents()){
            if(rent.getStartDate().isEqual(checkedRent.getStartDate()) 
            || 
            rent.getEndDate().isEqual(checkedRent.getEndDate()) 
            || 
            (rent.getStartDate().isAfter(checkedRent.getStartDate()) && rent.getStartDate().isBefore(checkedRent.getEndDate())) 
            || 
            (rent.getEndDate().isAfter(checkedRent.getStartDate()) && rent.getEndDate().isBefore(checkedRent.getEndDate()))){
                throw new RentServiceException("rent", "Cannot rent car");
            }
        }
        
        rent.setRental(rental);
        rentRepository.save(rent);
        return rent;
    }
}
