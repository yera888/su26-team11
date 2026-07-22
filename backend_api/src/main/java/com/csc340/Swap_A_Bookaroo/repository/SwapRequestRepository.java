package com.csc340.Swap_A_Bookaroo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequestStatus;

@Repository
public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long> {

    List<SwapRequest> findByCustomerProfile_CustomerProfileId(
            Long customerProfileId);

    List<SwapRequest> findByBookListing_ProviderProfile_ProviderProfileId(
            Long providerProfileId);

    List<SwapRequest> findByBookListing_ListingIdAndStatusIn(
            Long listingId,
            List<SwapRequestStatus> statuses);

    List<SwapRequest>
            findByBookListing_ProviderProfile_ProviderProfileIdAndStatus(
                    Long providerProfileId,
                    SwapRequestStatus status);

    List<SwapRequest>
            findByBookListing_ProviderProfile_ProviderProfileIdAndStatusIn(
                    Long providerProfileId,
                    List<SwapRequestStatus> statuses);

    List<SwapRequest> findByCustomerProfile_CustomerProfileIdAndStatus(
            Long customerProfileId,
            SwapRequestStatus status);

    List<SwapRequest>
            findByCustomerProfile_Account_UsernameAndStatusOrderByRequestDateDesc(
                    String username,
                    SwapRequestStatus status);

    List<SwapRequest>
            findByBookListing_ProviderProfile_Account_UsernameAndStatusOrderByRequestDateDesc(
                    String username,
                    SwapRequestStatus status);

    List<SwapRequest>
            findByBookListing_ProviderProfile_Account_UsernameAndStatusInOrderByRequestDateDesc(
                    String username,
                    List<SwapRequestStatus> statuses);

    long countByBookListing_ProviderProfile_Account_UsernameAndStatus(
            String username,
            SwapRequestStatus status);

    boolean
            existsByCustomerProfile_CustomerProfileIdAndBookListing_ListingIdAndStatus(
                    Long customerProfileId,
                    Long listingId,
                    SwapRequestStatus status);
}