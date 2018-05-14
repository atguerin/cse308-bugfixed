package com.maple.cse308.repository;

import com.maple.cse308.entity.MovieScreenshot;
import com.maple.cse308.entity.TvScreenshot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TvScreenshotRepository extends CrudRepository<TvScreenshot, Integer> {
    List<TvScreenshot> findAllByTvId(Integer Id);
}
