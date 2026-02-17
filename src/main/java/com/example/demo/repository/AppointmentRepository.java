package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByNameAndDateAndTime(String name, String date, String time);
    List<Appointment> findByPropertyId(Long propertyId);
}
