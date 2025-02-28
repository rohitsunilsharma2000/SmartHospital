package com.hms.repository;


import com.hms.modal.PrescriptionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionTemplateRepository extends JpaRepository<PrescriptionTemplate, Long> {
    List<PrescriptionTemplate> findByTemplateNameContainingIgnoreCase( String templateName);
}

