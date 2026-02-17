package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Property;
import com.example.demo.repository.PropertyRepository;

@Controller
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping("/properties")
    public String listProperties(Model model) {
        model.addAttribute("properties", propertyRepository.findAll());
        return "property/property-list";
    }

    @GetMapping("/properties/{id}")
    public String propertyDetails(@PathVariable Long id, Model model) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid property Id:" + id));
        model.addAttribute("property", property);
        return "property/property-details";
    }

    @GetMapping("/property/add")
    public String showAddPropertyForm(Model model) {
        model.addAttribute("property", new Property());
        return "property/add-edit-property";
    }

    @GetMapping("/property/edit/{id}")
    public String showEditPropertyForm(@PathVariable Long id, Model model) {
        Property property = propertyRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid property Id:" + id));
        model.addAttribute("property", property);
        System.out.println("Fetched Property: " + property);
        return "property/add-edit-property";
    }

    @PostMapping("/property/save")
    public String saveProperty(@ModelAttribute Property property) {
        if (property.getId() == null) {
            // Add a new property
            propertyRepository.save(property);
        } else {
            // Update an existing property
            Property existingProperty = propertyRepository.findById(property.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid property Id:" + property.getId()));
            existingProperty.setName(property.getName());
            existingProperty.setLocation(property.getLocation());
            existingProperty.setPrice(property.getPrice());
            existingProperty.setDescription(property.getDescription());
            existingProperty.setBedrooms(property.getBedrooms());
            existingProperty.setBathrooms(property.getBathrooms());
            existingProperty.setSizeSqFt(property.getSizeSqFt());
            existingProperty.setPropertyType(property.getPropertyType());
            existingProperty.setAmenities(property.getAmenities());
            existingProperty.setImageUrl(property.getImageUrl());
            propertyRepository.save(existingProperty);
        }
        return "redirect:/properties";
    }
}
