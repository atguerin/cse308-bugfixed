package com.maple.cse308.controller;

import com.maple.cse308.entity.MovieReviewUser;
import com.maple.cse308.service.MovieServiceImpl;
import com.maple.cse308.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MovieController {

    @Autowired
    private MovieServiceImpl movieService;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/movies")
    public String movies(Model model) {
        model.addAttribute("listType", "OPENING THIS WEEK");
        model.addAttribute("selectedList", movieService.getMoviesOutNow());
        return "movies";
    }

    @GetMapping("/movies/opening")
    public String moviesOpeningSoon(Model model) {
        model.addAttribute("listType", "OUT NOW");
        model.addAttribute("selectedList", movieService.getMoviesOutNow());
        return "movies";
    }

    @GetMapping("/movies/topbox")
    public String moviesTopBoxOffice(Model model) {
        model.addAttribute("listType", "TOP BOX OFFICE");
        model.addAttribute("selectedList", movieService.getTopBoxOffice());
        return "movies";
    }

    @GetMapping("/movies/coming")
    public String moviesComingSoon(Model model) {
        model.addAttribute("listType", "COMING SOON");
        model.addAttribute("selectedList", movieService.getMoviesComingSoon());
        return "movies";
    }

    @PostMapping("/postMovieReview")
    public String postReview(@ModelAttribute MovieReviewUser reviewUser, Model model) throws Exception {
        reviewUser.setUserId(userService.getCurrentUser().getUserId());
        movieService.addUserMovieReview(reviewUser);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully posted your review!");
        return "movie_details :: serverResponseModalContent";
    }


}























