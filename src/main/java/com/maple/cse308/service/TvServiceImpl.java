package com.maple.cse308.service;

import com.maple.cse308.entity.TvReviewCritic;
import com.maple.cse308.entity.TvReviewUser;
import com.maple.cse308.entity.TvShow;
import com.maple.cse308.repository.TvReviewCriticRepository;
import com.maple.cse308.repository.TvReviewUserRepository;
import com.maple.cse308.repository.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

public class TvServiceImpl implements TvService {

    @Autowired
    TvReviewCriticRepository tvReviewCriticRepository;
    @Autowired
    TvReviewUserRepository tvReviewUserRepository;
    @Autowired
    private TvShowRepository tvShowRepository;

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
    public List<TvReviewCritic> getCriticTvReviewsByTvShow(int tvId) throws Exception {
        List<TvReviewCritic> tvReviewCritics = tvReviewCriticRepository.findAllByTvId(tvId);
        if (tvReviewCritics.isEmpty()) {
            throw new Exception("Error: There are no reviews for this tv show");
        } else {
            return tvReviewCritics;
        }
    }

    @Override
    public List<TvReviewCritic> getCriticTvReviewsByCritic(int criticId) throws Exception {
        List<TvReviewCritic> tvReviewCritics = tvReviewCriticRepository.findAllByTvId(criticId);
        if (tvReviewCritics.isEmpty()) {
            throw new Exception("Error: There are no reviews for this tv show");
        } else {
            return tvReviewCritics;
        }
    }

    @Override
    public List<TvReviewUser> getUserTvReviewsByTvShow(int tvId) throws Exception {
        List<TvReviewUser> tvReviewUsers = tvReviewUserRepository.findAllByTvId(tvId);
        if (tvReviewUsers.isEmpty()) {
            throw new Exception("Error: There are no reviews for this tv show");
        } else {
            return tvReviewUsers;
        }
    }

    @Override
    public List<TvReviewUser> getUserTvReviewsByUser(int userId) throws Exception {
        List<TvReviewUser> tvReviewUsers = tvReviewUserRepository.findAllByTvId(userId);
        if (tvReviewUsers.isEmpty()) {
            throw new Exception("Error: There are no reviews for this tv show");
        } else {
            return tvReviewUsers;
        }
    }

    @Override
    public List<TvShow> findAllByTitleContainingIgnoreCase(String search) {
        return tvShowRepository.findAllByTitleContainingIgnoreCase(search);
    }

    @Override
    public List<TvShow> tvSearch(String search) {
        //String needs to be parsed, and removed for duplcates.
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
}
