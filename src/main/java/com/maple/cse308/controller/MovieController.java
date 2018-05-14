package com.maple.cse308.controller;

import com.maple.cse308.entity.MovieReviewUser;
import com.maple.cse308.service.MovieServiceImpl;
import com.maple.cse308.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String postReview(@ModelAttribute MovieReviewUser reviewUser, Model model) {
        reviewUser.setUserId(userService.getCurrentUser().getUserId());
        movieService.addUserMovieReview(reviewUser);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully posted your review!");
        return "movie_details :: serverResponseModalContent";
    }

    @RequestMapping(value = "/deleteMovieReview")
    public String deleteReview(@RequestParam("reviewId") String id, Model model) {
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully deleted your review!");
        int num = Integer.parseInt(id);
        movieService.deleteUserMovieReview(num);
        return "movie_details :: serverResponseModalContent";
    }

    @RequestMapping(value = "/editMovieReview")
    public String changeReview(@ModelAttribute MovieReviewUser reviewUser, Model model) {
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully posted your review!");
        MovieReviewUser mru = movieService.getMovieReviewUser(reviewUser.getReviewId());
        reviewUser.setReviewId(mru.getReviewId());
        reviewUser.setLink(mru.getLink());
        reviewUser.setMovieId(mru.getMovieId());
        reviewUser.setUserId(mru.getUserId());
        reviewUser.setUser(mru.getUser());
        movieService.deleteUserMovieReview(reviewUser.getReviewId());
        movieService.addUserMovieReview(reviewUser);
        return "movie_details:: serverResponseModalContent";
    }
}























