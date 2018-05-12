package com.maple.cse308.entity;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Set;

@Entity
@Table(name="TvShow")
public class TvShow {

    @Id
    @Column(name="tvId")
    private Integer tvId;

    @Column(name="title")
    private String title;

    @Column(name="premierDate")
    private Date premierDate;

    @Column(name="poster")
    private String poster;

    @Column(name="overview", length=2048)
    private String overview;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Column(name="rating")
    private String rating;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tvGenre", joinColumns = @JoinColumn(name = "tvId"), inverseJoinColumns = @JoinColumn(name = "genreId"))
    private Set<Genre> genre;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tvCreator", joinColumns = @JoinColumn(name = "tvId"), inverseJoinColumns = @JoinColumn(name = "creatorId"))
    private Set<Creator> creators;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "tvId")
    private Set<TvScreenshot> tvScreenshots;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tvId")
    private Set<TvReviewCritic> criticReviews;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tvId")
    private Set<TvReviewUser> userReviews;

    @ManyToMany(mappedBy = "watchListTV")
    private Set<User> usersWatchTV;

    @ManyToMany(mappedBy = "dontWatchListTV")
    private Set<User> usersDontWatchTV;

    public Integer getTvId() {
        return tvId;
    }

    public void setTvId(Integer tvId) {
        this.tvId = tvId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPremierDate() {
        return premierDate;
    }

    public void setPremierDate(Date premierDate) {
        this.premierDate = premierDate;
    }

    public String getDateString() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/yy");
        String date = dataFormat.format(premierDate);
        return date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Set<Genre> getGenre() {
        return genre;
    }

    public void setGenre(Set<Genre> genre) {
        this.genre = genre;
    }

    public Set<Creator> getCreators() {
        return creators;
    }

    public void setCreators(Set<Creator> creators) {
        this.creators = creators;
    }

    public Set<TvScreenshot> getTvScreenshots() {
        return tvScreenshots;
    }

    public void setTvScreenshots(Set<TvScreenshot> tvScreenshots) {
        this.tvScreenshots = tvScreenshots;
    }
}