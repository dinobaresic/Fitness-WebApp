package com.example.fitness.repository;


import com.example.fitness.model.FitnessCoach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessCoachRepository extends JpaRepository<FitnessCoach, Long> {
}