package com.csc340.Swap_A_Bookaroo.repository;
<<<<<<< HEAD

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
=======
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.csc340.Swap_A_Bookaroo.entities.*;

@Repository
public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long> {
    List<SwapRequest> findByBookListing_ProviderProfile_ProviderProfileIdAndStatus(Long id, SwapRequestStatus status);
    List<SwapRequest> findByBookListing_ProviderProfile_ProviderProfileIdAndStatusIn(Long id, List<SwapRequestStatus> statuses);
    List<SwapRequest> findByCustomerProfile_CustomerProfileIdAndStatus(Long customerProfileId, SwapRequestStatus status);
}
>>>>>>> origin/main
