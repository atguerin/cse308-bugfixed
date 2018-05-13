package com.maple.cse308.controller;

import com.maple.cse308.entity.Movie;
import com.maple.cse308.entity.MovieReviewUser;
import com.maple.cse308.entity.User;
import com.maple.cse308.service.MovieServiceImpl;
import com.maple.cse308.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/editMovieReview")
    public String editReview(@ModelAttribute MovieReviewUser reviewUser, Model model) throws Exception {
        int userId = userService.getCurrentUser().getUserId();
        MovieReviewUser review = movieService.getUserMovieReviewsByUserAndMovie(userId, reviewUser.getMovieId()).get(0);
        review.setRating(reviewUser.getRating());
        review.setReview(reviewUser.getReview());
        movieService.editUserMovieReview(review);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully edited your review!");
        return "movie_details :: serverResponseModalContent";
    }

    @GetMapping("/movie/reviewForm")
    public String updateMovieReviewForm(@RequestParam(value = "movieId") int movieId, Model model) {
        model.addAttribute("movie", movieService.getMovieDetails(movieId));
        int userId = userService.getCurrentUser().getUserId();
        model.addAttribute("review", movieService.getUserMovieReviewsByUserAndMovie(userId, movieId).get(0));
        return "movie_details :: reviewForm";
    }

    @GetMapping("/movie/reviews")
    public String updateMovieReviews(@RequestParam(value = "movieId") int movieId, Model model) {
        model.addAttribute("criticReviews", movieService.getCriticMovieReviewsByMovie(movieId));
        model.addAttribute("userReviews", movieService.getUserMovieReviewsByMovie(movieId));
        return "movie_details :: reviews";
    }


    @PostMapping("/movie/addToWantToSeeList")
    public String addToWantToSeeList(@RequestParam(value = "movieId") int movieId, Model model) throws Exception {
        Movie movie = movieService.getMovieDetails(movieId);
        userService.addToWantToSeeList(movie);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully added to your Want To See List!");
        return "movie_details :: serverResponseModalContent";
    }

    @PostMapping("/movie/addToDontWantToSeeList")
    public String addToDontWantToSeeList(@RequestParam(value = "movieId") int movieId, Model model) throws Exception {
        Movie movie = movieService.getMovieDetails(movieId);
        userService.addToDontWantToSeeList(movie);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully added to your Not Interested List!");
        return "movie_details :: serverResponseModalContent";
    }

    @PostMapping("/movie/deleteFromWantToSeeList")
    public String deleteFromWantToSeeList(@RequestParam(value = "id") int movieId, Model model) throws Exception {
        Movie movie = movieService.getMovieDetails(movieId);
        userService.removeFromWantToSeeList(movie);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully deleted from your Want To See List!");
        return "profile :: serverResponseModalContent";
    }

    @PostMapping("/movie/deleteFromNotInterestedList")
    public String deleteFromNotInterestList(@RequestParam(value = "id") int movieId, Model model) throws Exception {
        Movie movie = movieService.getMovieDetails(movieId);
        userService.removeFromDontWantToSeeList(movie);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully deleted from your Not Interest List!");
        return "profile :: serverResponseModalContent";
    }

    @PostMapping("/movie/deleteMovieUserReview")
    public String deleteMovieUserReview(@RequestParam(value = "id") int reviewId, Model model) throws Exception {
        movieService.deleteUserMovieReview(reviewId);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully deleted your review!");
        return "profile :: serverResponseModalContent";
    }

    @GetMapping("/movie/wantToSeeMovieList")
    public String getWantToSeeMovie(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "profile :: wantToSeeMovie";
    }

    @GetMapping("/movie/notInterestedList")
    public String getNotInterestMovie(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "profile :: notInterestedMovie";
    }

    @GetMapping("/movie/userMovieReviews")
    public String getUserMovieReviews(Model model) {
        User user = userService.getCurrentUser();
        List<MovieReviewUser> movieReviews = movieService.getUserMovieReviewsByUser(user.getUserId());
        model.addAttribute("movieReviews", movieReviews);
        return "profile :: movieReviews";
    }

    @RequestMapping("/movie_trailers")
    public String movieDetails(@RequestParam(value = "id", required = false) int id, Model model) {
        model.addAttribute("movie", movieService.getMovieDetails(id));
        model.addAttribute("trailers", movieService.getMovieTrailers(id));
        return "movie_trailers";
    }


}























