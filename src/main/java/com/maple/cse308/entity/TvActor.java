package com.maple.cse308.entity;

import javax.persistence.*;

@Entity
@Table(name="TvActor")
public class TvActor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tvActorId")
    private Integer tvActorId;

    @Column(name = "tvId")
    private Integer tvId;

    @Column(name = "actorId")
    private Integer actorId;

    @Column(name = "role", length=128)
    private String role;

    @Column(name="needsChecking")
    private Boolean needsChecking;

    public Integer getTvActorId() {
        return tvActorId;
    }

    public void setvActorId(Integer tvActorId) {
        this.tvActorId = tvActorId;
    }

    public Integer getTvId() {
        return tvId;
    }

    public void setTvId(Integer tvId) {
        this.tvId = tvId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getNeedsChecking() {
        return needsChecking;
    }

    public void setNeedsChecking(Boolean needsChecking) {
        this.needsChecking = needsChecking;
    }
}
