package com.maple.cse308.repository;

import com.maple.cse308.entity.Episode;
import com.maple.cse308.entity.EpisodeIdentity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends CrudRepository<Episode, EpisodeIdentity> {

    Episode save(Episode episode);

    List<Episode> findAllByTvIdAndSeason(int tvId, int season);
}
