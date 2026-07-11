package com.csc340.Swap_A_Bookaroo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;

@Repository
public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Long> {
    
    // Finds a provider profile using the linked Account's ID
    Optional<ProviderProfile> findByAccountId(Long accountId);
}