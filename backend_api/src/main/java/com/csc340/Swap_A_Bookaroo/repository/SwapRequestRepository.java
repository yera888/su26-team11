package com.csc340.Swap_A_Bookaroo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;

@Repository
public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long> {
    List<SwapRequest> findByCustomerProfileIdAndStatus(Long customerProfileId, String status);
}
