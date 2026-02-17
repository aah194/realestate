package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/reviews")
    public String listReviews(Model model) {
        model.addAttribute("reviews", reviewRepository.findAll());
        return "review/review-list";
    }

    @GetMapping("/reviews/add")
    public String showAddReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "review/add-review";
    }

    @PostMapping("/reviews/add")
    public String addReview(@ModelAttribute Review review) {
        reviewRepository.save(review);
        return "redirect:/reviews";
    }
}
