package be.ucll.se.groep02backend.bill.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.se.groep02backend.bill.model.Bill;
import be.ucll.se.groep02backend.bill.service.BillService;
import be.ucll.se.groep02backend.bill.service.BillServiceException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;



@Tag(name = "Bill")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/bill")
public class BillRestController {

    @Autowired
    private BillService billService;

    @GetMapping
    public List<Bill> getBills() {
        return billService.getAllBills();
    }

    @GetMapping("/email/")
    public List<Bill> getBillsByEmail(String email) throws BillServiceException {
        return billService.getBillsByEmail(email);
    }
    
}
