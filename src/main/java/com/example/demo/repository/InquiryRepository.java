package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.model.Inquiry;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    boolean existsByNameAndMessageAndMobileno(String name, String message, String mobileno);

    @Query("SELECT i FROM Inquiry i LEFT JOIN Property p ON i.propertyId = p.id")
    List<Inquiry> findAllWithProperty();
}
