package com.example.fitness.controller;

import com.example.fitness.model.FitnessCoach;
import com.example.fitness.service.FitnessCoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class FitnessCoachController {

    @Autowired
    private FitnessCoachService fitnessCoachService;

    @GetMapping
    public List<FitnessCoach> getAllCoaches() {
        return fitnessCoachService.findAll();
    }

    @PostMapping
    public FitnessCoach addCoach(@RequestBody FitnessCoach fitnessCoach) {
        return fitnessCoachService.save(fitnessCoach);
    }

}
