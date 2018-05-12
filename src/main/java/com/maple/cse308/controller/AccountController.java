package com.maple.cse308.controller;

import com.maple.cse308.entity.User;
import com.maple.cse308.service.MovieServiceImpl;
import com.maple.cse308.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AccountController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MovieServiceImpl movieService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("comingSoonList", movieService.getMoviesComingSoon());
        model.addAttribute("outNowList", movieService.getMoviesOutNow());
        model.addAttribute("topBoxOfficeList", movieService.getTopBoxOffice());
        return "index";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("comingSoonList", movieService.getMoviesComingSoon());
        model.addAttribute("outNowList", movieService.getMoviesOutNow());
        model.addAttribute("topBoxOfficeList", movieService.getTopBoxOffice());
        return "index";
    }

    @PostMapping("/registration")
    public String registerSubmit(@ModelAttribute User user, Model model){
        try {
            userService.registerUser(user);
        } catch (Exception e) {
            model.addAttribute("comingSoonList", movieService.getMoviesComingSoon());
            model.addAttribute("outNowList", movieService.getMoviesOutNow());
            model.addAttribute("topBoxOfficeList", movieService.getTopBoxOffice());
            return "redirect:/index";

        }
        model.addAttribute("comingSoonList", movieService.getMoviesComingSoon());
        model.addAttribute("outNowList", movieService.getMoviesOutNow());
        model.addAttribute("topBoxOfficeList", movieService.getTopBoxOffice());
        return "redirect:/index";
    }

    @RequestMapping("/manage_account")
    public String manageAccount(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "manage_account";
    }

    @PostMapping("/changeHomeTown")
    public String changeHomeTown(@RequestParam String hometown, Model model) {
        try {
            userService.changeHomeTown(hometown);
        } catch (Exception e) {
            return "manage_account";
        }
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("response", "Changes are saved!");
        return "manage_account :: hometownSetting";
    }

    @PostMapping("/changeBio")
    public String changeBio(@RequestParam String bio, Model model) {
        try {
            userService.changeBio(bio);
        } catch (Exception e) {
            return "manage_account";
        }
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("response", "Changes are saved!");
        return "manage_account :: bioSetting";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Model model) {
        User user = userService.getCurrentUser();
        try {
            userService.changePassword(oldPassword, newPassword);
        } catch (Exception e) {
            return "manage_account";
        }
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("response", "Changes are saved!");
        return "manage_account :: passwordSetting";
    }

    @PostMapping("/changeCountry")
    public String changeCountry(@RequestParam String country, Model model) {
        try {
            userService.changeCountry(country);
        } catch (Exception e) {
            return "manage_account";
        }
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("response", "Changes are saved!");
        return "manage_account :: changeCountry";
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(Model model) {
        userService.deleteUser();
        SecurityContextHolder.clearContext();
        model.addAttribute("deleted", true);
        return "manage_account :: deleteAccount";
    }
}