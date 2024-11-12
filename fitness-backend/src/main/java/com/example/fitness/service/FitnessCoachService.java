package com.example.fitness.service;


import com.example.fitness.model.FitnessCoach;
import com.example.fitness.repository.FitnessCoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FitnessCoachService {

    @Autowired
    private FitnessCoachRepository fitnessCoachRepository;

    public List<FitnessCoach> findAll() {
        return fitnessCoachRepository.findAll();
    }

    public FitnessCoach save(FitnessCoach fitnessCoach) {
        return fitnessCoachRepository.save(fitnessCoach);
    }


}