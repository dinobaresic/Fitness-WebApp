package com.example.fitness.repository;

import com.example.fitness.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>{
    Optional<Client> findById(String id);
    @Query("SELECT c FROM Client c WHERE c.user.id = :userId")
    Page<Client> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
