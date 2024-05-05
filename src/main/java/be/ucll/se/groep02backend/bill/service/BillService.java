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
        } 
        if (authenticatedUser.getRoles().contains(Role.OWNER) || authenticatedUser.getRoles().contains(Role.RENTER)) {
            return billRepository.findAllBillsByRenterEmailOrOwnerEmail(authenticatedUser.getEmail());
        }
        else {
            throw new BillServiceException("bill","You are not authorized to view all bills");
        }
    }

    // Only admin or accountant can set bill as paid
    public Bill setBillPaid(Long id, User user) throws BillServiceException {
        if (user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.ACCOUNTANT)) {
            Bill bill = billRepository.findBillById(id);
            bill.setPaid(true);
            return billRepository.save(bill);
        } else {
            throw new BillServiceException("role", "User or owner is not authorized to set bill as paid");
        }
    }

    // Get total earnings paid
    public double getTotalEarningsPaid(User user) throws BillServiceException {
        double totalAmount = 0;
        if (user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.ACCOUNTANT)) {
            List<Bill> bills = billRepository.findAll();
            for (Bill bill : bills) {
                if (bill.isPaid()) {
                    totalAmount += bill.getTotal();
                }
            }
            return totalAmount;
        } if (user.getRoles().contains(Role.OWNER)) {
            List<Bill> bills = billRepository.findAllBillsByRenterEmailOrOwnerEmail(user.getEmail());
            for (Bill bill : bills) {
                if (bill.isPaid()) {
                    totalAmount += bill.getTotal();
                }
            }
            return totalAmount;
        } else {
            throw new BillServiceException("role", "User is not authorized to view total earnings");
        }
    }

    // Get total earnings unpaid
    public double getTotalEarningsUnPaid(User user) throws BillServiceException {
        double totalAmount = 0;
        if (user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.ACCOUNTANT)) {
            List<Bill> bills = billRepository.findAll();
            for (Bill bill : bills) {
                if (!bill.isPaid()) {
                    totalAmount += bill.getTotal();
                }
            }
            return totalAmount;
        } if (user.getRoles().contains(Role.OWNER)) {
            List<Bill> bills = billRepository.findAllBillsByRenterEmailOrOwnerEmail(user.getEmail());
            for (Bill bill : bills) {
                if (!bill.isPaid()) {
                    totalAmount += bill.getTotal();
                }
            }
            return totalAmount;
        } else {
            throw new BillServiceException("role", "User is not authorized to view total unpaid earnings");
        }
    }
}
