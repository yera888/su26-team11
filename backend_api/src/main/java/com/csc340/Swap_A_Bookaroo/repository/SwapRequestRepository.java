package com.csc340.Swap_A_Bookaroo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequestStatus;

@Repository
public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long> {

    List<SwapRequest> findByBookListing_ProviderProfile_ProviderProfileIdAndStatus(Long providerProfileId,
            SwapRequestStatus status);

}
