package com.maple.cse308.service;

import com.maple.cse308.entity.Critic;

import java.util.List;

public interface CriticService {

    List<Critic> criticSearch(String search);
}
