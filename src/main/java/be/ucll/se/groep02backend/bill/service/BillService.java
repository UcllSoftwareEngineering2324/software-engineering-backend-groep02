package be.ucll.se.groep02backend.bill.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.se.groep02backend.bill.model.Bill;
import be.ucll.se.groep02backend.bill.repo.BillRepository;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> getBillsByEmail(String email) throws BillServiceException {

        List<Bill> bills = billRepository.findAllBillsByRenterEmailOrOwnerEmail(email);

        if (bills.isEmpty()) {
            throw new BillServiceException("bill","No bills found for email: " + email);
        }
        
        return bills;
    }
}
