package be.ucll.se.groep02backend.bill.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.bill.model.Bill;
import be.ucll.se.groep02backend.bill.repo.BillRepository;
import be.ucll.se.groep02backend.user.model.Role;
import be.ucll.se.groep02backend.user.model.User;

import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public List<Bill> getAllBills(User authenticatedUser) throws BillServiceException {
        if (authenticatedUser.getRoles().contains(Role.ADMIN) || authenticatedUser.getRoles().contains(Role.ACCOUNTANT)) {
            return billRepository.findAll();
        } else {
            throw new BillServiceException("bill","User is not authorized to view all bills");
        }
    }

    public List<Bill> getBillsByEmail(String email) throws BillServiceException {

        List<Bill> bills = billRepository.findAllBillsByRenterEmailOrOwnerEmail(email);

        if (bills.isEmpty()) {
            throw new BillServiceException("bill","No bills found for email: " + email);
        }
        
        return bills;
    }
}
