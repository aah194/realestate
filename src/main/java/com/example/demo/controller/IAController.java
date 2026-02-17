package com.example.demo.controller;

import java.util.List;

import org.hibernate.mapping.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Appointment;
import com.example.demo.model.Inquiry;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.InquiryRepository;
import com.example.demo.repository.PropertyRepository;

@Controller
public class IAController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private PropertyRepository propertyRepository;
    
    @GetMapping("/inquiry")
    public String inquiry() {
        return "inquiry"; // Make sure this maps to the correct view name
    }


    @GetMapping("/appointment")
    public String appointment(Model model) {
        model.addAttribute("properties", propertyRepository.findAll());
        return "appointment";
    }

    @PostMapping("/saveAppointment")
    public String saveAppointment(
            @RequestParam("name") String name,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("mobileno") String mobileno,
            @RequestParam(value = "propertyId", required = false) Long propertyId,
            RedirectAttributes redirectAttributes) {

        if (!appointmentRepository.existsByNameAndDateAndTime(name, date, time)) {
            Appointment appointment = new Appointment();
            appointment.setName(name);
            appointment.setDate(date);
            appointment.setTime(time);
            appointment.setMobileno(mobileno);
            appointment.setPropertyId(propertyId);
            appointmentRepository.save(appointment);
            redirectAttributes.addFlashAttribute("message", "Appointment saved successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Appointment already exists.");
        }

        return "redirect:/appointment";
    }

    @PostMapping("/saveInquiry")
    public String saveInquiry(
            @RequestParam("name") String name,
            @RequestParam("message") String message,
            @RequestParam("mobileno") String mobileno,
            @RequestParam(value = "propertyId", required = false) Long propertyId,
            RedirectAttributes redirectAttributes) {

        if (!inquiryRepository.existsByNameAndMessageAndMobileno(name, message, mobileno)) {
            Inquiry inquiry = new Inquiry();
            inquiry.setName(name);
            inquiry.setMessage(message);
            inquiry.setMobileno(mobileno);
            inquiry.setPropertyId(propertyId);
            inquiryRepository.save(inquiry);
            redirectAttributes.addFlashAttribute("message", "Inquiry saved successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Inquiry already exists.");
        }

        return "redirect:/inquiry";
    }
}
