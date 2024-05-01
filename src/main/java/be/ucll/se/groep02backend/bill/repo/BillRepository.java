package be.ucll.se.groep02backend.bill.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.se.groep02backend.bill.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long>{
    public List<Bill> findAll();
}
