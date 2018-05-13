package com.maple.cse308.controller;

import com.maple.cse308.entity.*;
import com.maple.cse308.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StaticController {

    @Autowired
    private MovieServiceImpl movieService;
    @Autowired
    private ActorServiceImpl actorService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CriticServiceImpl criticService;


    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("comingSoonList", movieService.getMoviesComingSoon());
        model.addAttribute("outNowList", movieService.getMoviesOutNow());
        model.addAttribute("topBoxOfficeList", movieService.getTopBoxOffice());
        return "index";
    }

    @RequestMapping("/tv")
    public String tv(Model model) {
        return "tv";
    }

    @RequestMapping("/movie_details")
    public String movieDetails(@RequestParam(value = "id", required = false) int id, Model model) {
        model.addAttribute("comingSoonList", movieService.getMoviesComingSoon());
        model.addAttribute("outNowList", movieService.getMoviesOutNow());
        model.addAttribute("topBoxOfficeList", movieService.getTopBoxOffice());
        MovieReviewUser review = null;
        try {
            User user = userService.getCurrentUser();
            List<MovieReviewUser> l = movieService.getUserMovieReviewsByUserAndMovie(user.getUserId(), id);
            if (!l.isEmpty()){
                review = l.get(0);
            }
        } catch (Exception e){

        }
        model.addAttribute("movie", movieService.getMovieDetails(id));
        model.addAttribute("review", review);
        model.addAttribute("criticReviews", movieService.getCriticMovieReviewsByMovie(id));
        model.addAttribute("userReviews", movieService.getUserMovieReviewsByMovie(id));
        model.addAttribute("screenshots", movieService.getMovieScreenShots(id));
        model.addAttribute("trailers", movieService.getMovieTrailers(id));
        model.addAttribute("actors", movieService.getMovieActors(id));
        return "movie_details";
    }

    @RequestMapping("/tv_details")
    public String tvDetails(Model model) {
        return "tv_details";
    }

    @RequestMapping("/actor_details")
    public String actorDetails(@RequestParam(value = "id", required = false) int id, Model model) {
        model.addAttribute("comingSoonList", movieService.getMoviesComingSoon());
        model.addAttribute("outNowList", movieService.getMoviesOutNow());
        model.addAttribute("topBoxOfficeList", movieService.getTopBoxOffice());
        model.addAttribute("actor", actorService.getActorDetails(id));
        model.addAttribute("movies", actorService.getActorMovies(id));
        model.addAttribute("screenshots", actorService.getActorScreenshots(id));
        return "actor_details";
    }

    @RequestMapping("/profile")
    public String profile(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        List<MovieReviewUser> movieReviews = movieService.getUserMovieReviewsByUser(user.getUserId());
        model.addAttribute("movieReviews", movieReviews);
        //model.addAttribute("blockList", userService.getBlockList(user.getUserId()));
        return "profile";
    }

    @RequestMapping("/rating-test")
    public String rating(Model model) {
        return "rating-test";
    }


    @GetMapping("/search/all")
    public String searchAll(@RequestParam(value = "search") String search, Model model) {
        List<Movie> movieList = movieService.findAllByTitleContainingIgnoreCase(search);
        model.addAttribute("search", search);
        model.addAttribute("movieList", movieList);
        List<Movie> tvList = new ArrayList<>(); // We don't have TV data currently
        model.addAttribute("tvList", tvList);
        model.addAttribute("celebList", actorService.actorSearch(search));
        return "search";
    }

    @GetMapping("/search/movies")
    public String searchMovies(@RequestParam(value = "search") String search, Model model) {
        List<Movie> movieList = movieService.movieSearch(search);
        model.addAttribute("search", search);
        model.addAttribute("movieList", movieList);
        return "search :: movieList";
    }

    @GetMapping("/search/tv")
    public String searchTV(@RequestParam(value = "search") String search, Model model) {
        model.addAttribute("search", search);
        List<Movie> tvList = new ArrayList<>(); // We don't have TV data currently
        model.addAttribute("tvList", tvList);
        return "search :: tvList";
    }

    @GetMapping("/search/celeb")
    public String searchCelebrities(@RequestParam(value = "search") String search, Model model) {
        List<Actor> actorList = actorService.actorSearch(search);
        model.addAttribute("search", search);
        model.addAttribute("celebList", actorList);
        return "search :: celebList";
    }

    @GetMapping("/search/critic")
    public String searchCritics(@RequestParam(value = "search") String search, Model model) {
        List<Critic> criticList = criticService.criticSearch(search);
        model.addAttribute("search", search);
        model.addAttribute("criticList", criticList);
        return "search :: criticList";
    }

    @GetMapping("/userInformation")
    public String profile(@RequestParam(value = "userName") String userName, Model model) {
        model.addAttribute("user", userService.findByUsername(userName));
        return "user_info";
    }

    @RequestMapping("/contactUs")
    public String contactUs(Model model) {
        return "contact_us_form";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        return "about";
    }

}
