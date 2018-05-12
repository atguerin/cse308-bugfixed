package com.maple.cse308.controller;

import com.maple.cse308.entity.TvScreenshot;
import com.maple.cse308.service.TvService;
import com.maple.cse308.service.TvServiceImpl;
import com.maple.cse308.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TvController {
    @Autowired
    private TvServiceImpl TvService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/tv")
    public String tvs(Model model) {
        model.addAttribute("listType", "NEW TV TONIGHT");
        model.addAttribute("selectedList", TvService.getOpenThisWeek());
        return "tv";
    }

    @GetMapping("/tv/open")
    public String tvsopen(Model model) {
        model.addAttribute("listType", "NEW TV TONIGHT");
        model.addAttribute("selectedList", TvService.getOpenThisWeek());
        return "tv";
    }

    @GetMapping("/tv/popular")
    public String tvsPopular(Model model){
        model.addAttribute("listType", "MOST POPULAR MOVIE");
        model.addAttribute("selectedList", TvService.getPopularTv());
        return "tv";
    }


}
