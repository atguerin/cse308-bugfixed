package com.maple.cse308.service;

import com.maple.cse308.entity.Movie;
import com.maple.cse308.entity.TvShow;
import com.maple.cse308.entity.User;
import com.maple.cse308.enums.Visibility;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    String changePhoto(String photo);

    String changeCountry(String country) throws Exception;

    String changePassword(String oldpass, String newpass) throws Exception;

    String changeHomeTown(String homeTown) throws Exception;

    String changeBio(String bio) throws Exception;

    String changeVisibility(Visibility visibility);

    void deleteUser();

    void deleteUser(String username) throws Exception;

    User findByUsername(String username);

    User getCurrentUser();

    void registerUser(User user) throws Exception;

    void addEmployee(User user) throws Exception;

    void removeEmployee(User user) throws Exception;

    void banUser(User user) throws Exception;

    void suspendUser(User user, Calendar calendar) throws Exception;

    void addToBlocklist(String username) throws Exception;

    void removeFromBlocklist(String username) throws Exception;

    Set<Movie> getWantToSeeList();

    void addToWantToSeeList(Movie movie) throws Exception;

    void removeFromWantToSeeList(Movie movie) throws Exception;

    Set<Movie> getDontWantToSeeList();

    void addToDontWantToSeeList(Movie movie) throws Exception;

    void removeFromDontWantToSeeList(Movie movie) throws Exception;

    void addMovie(Movie movie) throws Exception;

    void editMovie(Movie movie) throws Exception;

    void addTvShow(TvShow tvShow) throws Exception;

    void editTvShow(TvShow tvShow) throws Exception;
}
