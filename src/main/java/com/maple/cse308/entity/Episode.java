package com.maple.cse308.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Episode")
public class Episode {

    @EmbeddedId
    private EpisodeIdentity episodeId;

    @Column(name = "tvId", insertable = false, updatable = false)
    private int tvId;

    public int getTvId() {
        return tvId;
    }

    public void setTvId(int tvId) {
        this.tvId = tvId;
    }
    @Column(name = "season", insertable = false, updatable = false)
    private int season;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    @Column(name = "title")
    private String title;

    @Column(name = "overview", length = 2048)
    private String overview;

    public EpisodeIdentity getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(EpisodeIdentity episodeId) {
        this.episodeId = episodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
