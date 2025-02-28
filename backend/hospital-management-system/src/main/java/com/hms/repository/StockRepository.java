package com.hms.repository;


import com.hms.service.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByMedicineId( Long medicineId);
    List<Stock> findByAvailableQuantityLessThanEqual(Integer reorderLevel);
}
