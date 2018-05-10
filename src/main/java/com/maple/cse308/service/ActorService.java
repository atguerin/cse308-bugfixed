package com.maple.cse308.service;

import com.maple.cse308.entity.Actor;

import java.util.List;

public interface ActorService {

    Actor getActorDetails(int actor_id);

    List<Actor> actorSearch(String search);
}
