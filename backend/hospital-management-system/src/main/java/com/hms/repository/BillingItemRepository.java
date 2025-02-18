package com.hms.repository;


import com.hms.modal.BillingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingItemRepository extends JpaRepository<BillingItem, Long> {
}
