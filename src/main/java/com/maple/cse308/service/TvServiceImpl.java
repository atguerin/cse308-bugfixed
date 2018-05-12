package com.maple.cse308.service;

import com.maple.cse308.entity.*;
import com.maple.cse308.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TvServiceImpl implements TvService {

    @Autowired
    TvReviewCriticRepository tvReviewCriticRepository;
    @Autowired
    TvReviewUserRepository tvReviewUserRepository;
    @Autowired
    private TvShowRepository tvShowRepository;
    @Autowired
    private TvScreenshotRepository tvScreenshotRepository;
    @Autowired
    private TvActorRepository tvActorRepository;
    @Autowired
    private CreatorRepository creatorRepository;

    @Override
    public TvShow getTvShowDetails(int tvId) {
        TvShow tvShow = tvShowRepository.findTvShowByTvId(tvId);
        return tvShow;
    }
    @Override
    public void addCriticTvReview(TvReviewCritic tvReviewCritic) {
        tvReviewCriticRepository.save(tvReviewCritic);
    }

    @Override
    public void editCriticTvReview(TvReviewCritic tvReviewCritic) {
        tvReviewCriticRepository.save(tvReviewCritic);
    }

    @Override
    public void deleteCriticTvReview(TvReviewCritic tvReviewCritic) {
        tvReviewCriticRepository.delete(tvReviewCritic);
    }

    @Override
    public void addUserTvReview(TvReviewUser tvReviewUser) {
        tvReviewUserRepository.save(tvReviewUser);
    }

    @Override
    public void editUserTvReview(TvReviewUser tvReviewUser) {
        tvReviewUserRepository.save(tvReviewUser);

    }

    @Override
    public void deleteUserTvReview(TvReviewUser tvReviewUser) {
        tvReviewUserRepository.save(tvReviewUser);
    }

    @Override
    public List<TvReviewCritic> getCriticTvReviewsByTvShow(int tvId)  {
        List<TvReviewCritic> tvReviewCritics = tvReviewCriticRepository.findAllByTvId(tvId);
        return tvReviewCritics;

    }

    @Override
    public List<TvReviewCritic> getCriticTvReviewsByCritic(int criticId) {
        List<TvReviewCritic> tvReviewCritics = tvReviewCriticRepository.findAllByTvId(criticId);
        return tvReviewCritics;
    }

    @Override
    public List<TvReviewUser> getUserTvReviewsByTvShow(int tvId)  {
        List<TvReviewUser> tvReviewUsers = tvReviewUserRepository.findAllByTvId(tvId);
        return tvReviewUsers;
    }

    @Override
    public List<TvReviewUser> getUserTvReviewsByUser(int userId) {
        List<TvReviewUser> tvReviewUsers = tvReviewUserRepository.findAllByTvId(userId);
        return tvReviewUsers;
    }

    @Override
    public List<TvShow> findAllByTitleContainingIgnoreCase(String search) {
        return tvShowRepository.findAllByTitleContainingIgnoreCase(search);
    }

    @Override
    public List<TvShow> tvSearch(String search) {
        String[] searchString;
        if (search.contains(" ")) {
            searchString = search.split(" ");
        } else {
            searchString = new String[1];
            searchString[0] = search;
        }
        String longest = searchString[0];
        for (String string : searchString) {
            if (string.length() > longest.length()) {
                longest = string;
            }
        }

        List<TvShow> tvShowList = tvShowRepository.findAllByTitleContainingIgnoreCase(longest);
        List<TvShow> resultList = new LinkedList();
        resultList.addAll(tvShowList);
        for (String string : searchString) {
            string = string.toLowerCase();
            for (TvShow tvShow : tvShowList) {
                String tvShowTitle = tvShow.getTitle().toLowerCase();
                if (!tvShowTitle.contains(string)) {
                    resultList.remove(tvShow);
                }
            }
        }
        return resultList;
    }

    public List<TvShow> getOpenThisWeek(){
        return tvShowRepository.findTop16ByOrderByPremierDateDesc();
    }

    public List<TvShow> getPopularTv(){
        return tvShowRepository.findTop16ByOrderByRatingDesc();
    }

    public List<TvScreenshot> getTvScreenshot(int id){
        return tvScreenshotRepository.findAllByTvId(id);
    }

    public List<TvActor> getTvActor(int id){
        return tvActorRepository.findAllByTvId(id);
    }

    public List<Creator> getTvCreator(int id){return creatorRepository.findAllByCreatorId(id);}
}
