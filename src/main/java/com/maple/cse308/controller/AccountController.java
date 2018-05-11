package com.maple.cse308.controller;

import com.maple.cse308.entity.User;
import com.maple.cse308.service.EmailServiceImpl;
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

    @PostMapping("/changeHomeTown")
    public String changeHomeTown(@RequestParam String hometown) {
        try {
            userService.changeHomeTown(hometown);
        } catch (Exception e) {
            return "manage_account";
        }
        return "manage_account";
    }

    @PostMapping("/changeBio")
    public String changeBio(@RequestParam String bio) {
        try {
            userService.changeBio(bio);
        } catch (Exception e) {
            return "manage_account";
        }
        return "manage_account";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        User user = userService.getCurrentUser();
        try {
            userService.changePassword(oldPassword, newPassword);
        } catch (Exception e) {
            return "manage_account";
        }
        return "manage_account";
    }

    @PostMapping("/changeCountry")
    public String changeCountry(@RequestParam String country) {
        try {
            userService.changeCountry(country);
        } catch (Exception e) {
            return "manage_account";
        }
        return "manage_account";
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount() {
        userService.deleteUser();
        SecurityContextHolder.clearContext();
        return "redirect:index";
    }
}