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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StaticController {

    @Autowired
    private MovieServiceImpl movieService;
    @Autowired
    private TvServiceImpl tvService;
    @Autowired
    private ActorServiceImpl actorService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CriticServiceImpl criticService;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private ReportServiceImpl reportService;


    @RequestMapping("/")
    public String defaultPage(Model model) {
        model.addAttribute("comingSoonList", movieService.getMoviesComingSoon());
        model.addAttribute("outNowList", movieService.getMoviesOutNow());
        model.addAttribute("topBoxOfficeList", movieService.getTopBoxOffice());
        return "index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("comingSoonList", movieService.getMoviesComingSoon());
        model.addAttribute("outNowList", movieService.getMoviesOutNow());
        model.addAttribute("topBoxOfficeList", movieService.getTopBoxOffice());
        return "index";
    }

    @RequestMapping("/actor_details")
    public String actorDetails(@RequestParam(value = "id", required = false) int id, Model model) {
        model.addAttribute("comingSoonList", movieService.getMoviesComingSoon());
        model.addAttribute("outNowList", movieService.getMoviesOutNow());
        model.addAttribute("topBoxOfficeList", movieService.getTopBoxOffice());
        model.addAttribute("actor", actorService.getActorDetails(id));
        model.addAttribute("movies", actorService.getActorMovies(id));
        model.addAttribute("tvs", actorService.getActorTVs(id));
        model.addAttribute("screenshots", actorService.getActorScreenshots(id));
        return "actor_details";
    }

    @RequestMapping("/profile")
    public String profile(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        List<MovieReviewUser> movieReviews = movieService.getUserMovieReviewsByUser(user.getUserId());
        model.addAttribute("movieReviews", movieReviews);
        List<TvReviewUser> tvReviews = tvService.getUserTvReviewsByUser(user.getUserId());
        model.addAttribute("tvReviews", tvReviews);
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
    public String userInformation(@RequestParam(value = "userName") String userName, Model model) {
        User user = userService.findByUsername(userName);
        model.addAttribute("user", user);
        List<MovieReviewUser> movieReviews = movieService.getUserMovieReviewsByUser(user.getUserId());
        model.addAttribute("movieReviews", movieReviews);
        return "user_info";
    }

    @GetMapping("/criticHome")
    public String criticHome(@RequestParam(value = "criticId") int criticId, Model model) {
        Critic critic = criticService.getCriticById(criticId);
        model.addAttribute("critic", critic);
        List<MovieReviewCritic> movieReviews = movieService.getCriticMovieReviewsByCritic(criticId);
        model.addAttribute("movieReviews", movieReviews);
        List<TvReviewCritic> tvReviews = tvService.getCriticTvReviewsByCritic(criticId);
        model.addAttribute("tvReviews", tvReviews);
        return "critic_home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        return "about";
    }

    @GetMapping("/contactUs")
    public String contactUs(Model model) {
        return "contact_us_form";
    }

    @PostMapping("/contactUs")
    public String contactUs(@RequestParam(value = "name") String name, @RequestParam(value = "email") String email, @RequestParam(value = "subject") String subject, @RequestParam(value = "message") String message) {
        emailService.sendSimpleMessage("cse308teammaple@gmail.com", "Contact Form Submission: "+ subject, "Name: " + name + "\nEmail: " + email + "\n\nMessage: " + message);
        return "index";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model) {
        return "forgot_password";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(Model model, @RequestParam("email") String email, HttpServletRequest request) {
        try {
            userService.resetPasswordToken(email,request);
        } catch (Exception e) {

        }
        return "forgot_password";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(Model model, @RequestParam("token") String token) {
        model.addAttribute("token", token);
        return "reset_password";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(Model model, @RequestParam("token") String token, @RequestParam("password") String password) {
        System.out.println(token);
        try {
            userService.resetPassword(token,password);
        } catch (Exception e) {

        }
        return "index";
    }

    @RequestMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("reportedCritics", reportService.getCriticReports());
        model.addAttribute("reportedUsers", reportService.getUserReports());
        model.addAttribute("movieCriticReviews", reportService.getCriticMovieReports());
        model.addAttribute("movieUserReviews", reportService.getUserMovieReports());
        model.addAttribute("tvCriticReviews", reportService.getCriticTvReports());
        model.addAttribute("tvUserReviews", reportService.getUserTvReports());
        return "reports";
    }

    @PostMapping("/reportUser")
    public String reportUser(@RequestParam(value = "id") int id, @RequestParam(value = "reason") String reason, Model model) throws Exception {
        reportService.addUserReport(id, reason);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully reported the user!");
        return "user_info :: serverResponseModalContent";
    }

    @PostMapping("/reportCritic")
    public String reportCritic(@RequestParam(value = "id") int id, @RequestParam(value = "reason") String reason, Model model) throws Exception {
        reportService.addCriticReport(id, reason);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully reported the critic!");
        return "critic_home :: serverResponseModalContent";
    }

    // All the backend stuff have to be handled, currently it just deletes the reports form reported lists
    @PostMapping("/deleteCritic")
    public String deleteCritic(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteCriticReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully deleted the critic!");
        return "reports :: serverResponseModalContent";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteUserReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully deleted the user!");
        return "reports :: serverResponseModalContent";
    }

    @PostMapping("/deleteMovieCriticReview")
    public String deleteMovieCriticReview(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteCriticMovieReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully deleted the review!");
        return "reports :: serverResponseModalContent";
    }

    @PostMapping("/deleteMovieUserReview")
    public String deleteMovieUserReview(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteUserMovieReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully deleted the review!");
        return "reports :: serverResponseModalContent";
    }

    @PostMapping("/deleteTvCriticReview")
    public String deleteTvCriticReview(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteCriticTvReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully deleted the review!");
        return "reports :: serverResponseModalContent";
    }

    @PostMapping("/deleteTvUserReview")
    public String deleteTvUserReview(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteUserTvReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully deleted the review!");
        return "reports :: serverResponseModalContent";
    }

    @GetMapping("/reportedCritic")
    public String reportedCritics(Model model) {
        model.addAttribute("reportedCritics", reportService.getCriticReports());
        return "reports :: critics";
    }

    @GetMapping("/reportedUser")
    public String reportedUsers(Model model) {
        model.addAttribute("reportedUsers", reportService.getUserReports());
        return "reports :: users";
    }

    @GetMapping("/reportedMovieCriticReview")
    public String reportedMovieCriticReviews(Model model) {
        model.addAttribute("movieCriticReviews", reportService.getCriticMovieReports());
        return "reports :: movieCriticReviews";
    }

    @GetMapping("/reportedMovieUserReview")
    public String reportedMovieUserReviews(Model model) {
        model.addAttribute("movieUserReviews", reportService.getUserMovieReports());
        return "reports :: movieUserReviews";
    }

    @GetMapping("/reportedTvCriticReview")
    public String reportedTvCriticReviews(Model model) {
        model.addAttribute("tvCriticReviews", reportService.getCriticTvReports());
        return "reports :: movieCriticReviews";
    }

    @GetMapping("/reportedTvUserReview")
    public String reportedTvUserReviews(Model model) {
        model.addAttribute("tvUserReviews", reportService.getUserTvReports());
        return "reports :: movieUserReviews";
    }




    //dismiss reports

    @PostMapping("/dismissCritic")
    public String dismissCritic(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteCriticReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully dismissed the critic!");
        return "reports :: serverResponseModalContent";
    }

    @PostMapping("/dismissUser")
    public String dismissUser(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteUserReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully dismissed the user!");
        return "reports :: serverResponseModalContent";
    }

    @PostMapping("/dismissMovieCriticReview")
    public String dismissMovieCriticReview(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteCriticMovieReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully dismissed the review!");
        return "reports :: serverResponseModalContent";
    }

    @PostMapping("/dismissMovieUserReview")
    public String dismissMovieUserReview(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteUserMovieReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully dismissed the review!");
        return "reports :: serverResponseModalContent";
    }

    @PostMapping("/dismissTvCriticReview")
    public String dismissTvCriticReview(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteCriticTvReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully dismissed the review!");
        return "reports :: serverResponseModalContent";
    }

    @PostMapping("/dismissTvUserReview")
    public String dismissTvUserReview(@RequestParam(value = "id") int id, Model model) throws Exception {
        reportService.deleteUserTvReport(id);
        model.addAttribute("title", "Success");
        model.addAttribute("body", "Successfully dismissed the review!");
        return "reports :: serverResponseModalContent";
    }

}
