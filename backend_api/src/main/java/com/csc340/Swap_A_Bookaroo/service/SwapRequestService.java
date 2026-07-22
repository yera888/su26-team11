package com.csc340.Swap_A_Bookaroo.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.repository.*;

@Service
public class SwapRequestService {

    private final SwapRequestRepository swapRequestRepository;
    private final BookListingRepository bookListingRepository;
    private final CustomerProfileRepository customerProfileRepository;

    public SwapRequestService(
            SwapRequestRepository swapRequestRepository,
            BookListingRepository bookListingRepository,
            CustomerProfileRepository customerProfileRepository) {
        this.swapRequestRepository = swapRequestRepository;
        this.bookListingRepository = bookListingRepository;
        this.customerProfileRepository = customerProfileRepository;
    }

    public SwapRequest getSwapRequestById(Long requestId) {
        return swapRequestRepository.findById(requestId).orElse(null);
    }

    public List<SwapRequest> getPendingRequestsByCustomer(
            Long customerProfileId) {
        return swapRequestRepository
                .findByCustomerProfile_CustomerProfileIdAndStatus(
                        customerProfileId,
                        SwapRequestStatus.PENDING);
    }

    public List<SwapRequest> getPendingRequestsForCustomer(String username) {
        return swapRequestRepository
                .findByCustomerProfile_Account_UsernameAndStatusOrderByRequestDateDesc(
                        username,
                        SwapRequestStatus.PENDING);
    }

    public List<SwapRequest> getPendingRequestsForProvider(String username) {
        return swapRequestRepository
                .findByBookListing_ProviderProfile_Account_UsernameAndStatusOrderByRequestDateDesc(
                        username,
                        SwapRequestStatus.PENDING);
    }

    public List<SwapRequest> getApprovedRequestsForProvider(String username) {
        return swapRequestRepository
                .findByBookListing_ProviderProfile_Account_UsernameAndStatusOrderByRequestDateDesc(
                        username,
                        SwapRequestStatus.APPROVED);
    }

    public List<SwapRequest> getHistoryForProvider(String username) {
        return swapRequestRepository
                .findByBookListing_ProviderProfile_Account_UsernameAndStatusInOrderByRequestDateDesc(
                        username,
                        Arrays.asList(
                                SwapRequestStatus.COMPLETED,
                                SwapRequestStatus.REJECTED,
                                SwapRequestStatus.CANCELLED));
    }

    @Transactional
    public SwapRequest createSwapRequest(SwapRequest request) {
        if (request == null
                || request.getCustomerProfile() == null
                || request.getBookListing() == null) {
            return null;
        }

        CustomerProfile customer = customerProfileRepository
                .findById(request.getCustomerProfile().getCustomerProfileId())
                .orElse(null);
        BookListing listing = bookListingRepository
                .findById(request.getBookListing().getListingId())
                .orElse(null);

        return saveNewRequest(customer, listing);
    }

    @Transactional
    public SwapRequest createSwapRequestForCustomer(
            String username,
            Long listingId) {
        CustomerProfile customer = customerProfileRepository
                .findByAccount_Username(username)
                .orElse(null);
        BookListing listing = bookListingRepository
                .findById(listingId)
                .orElse(null);

        return saveNewRequest(customer, listing);
    }

    @Transactional
    public SwapRequest approveSwapRequest(Long requestId) {
        return approveExistingRequest(
                swapRequestRepository.findById(requestId).orElse(null));
    }

    @Transactional
    public SwapRequest approveSwapRequestForProvider(
            String username,
            Long requestId) {
        return approveExistingRequest(
                getOwnedProviderRequest(username, requestId));
    }

    @Transactional
    public SwapRequest rejectSwapRequest(Long requestId) {
        return rejectExistingRequest(
                swapRequestRepository.findById(requestId).orElse(null));
    }

    @Transactional
    public SwapRequest rejectSwapRequestForProvider(
            String username,
            Long requestId) {
        return rejectExistingRequest(
                getOwnedProviderRequest(username, requestId));
    }

    @Transactional
    public SwapRequest completeSwapRequest(Long requestId) {
        return completeExistingRequest(
                swapRequestRepository.findById(requestId).orElse(null));
    }

    @Transactional
    public SwapRequest completeSwapRequestForProvider(
            String username,
            Long requestId) {
        return completeExistingRequest(
                getOwnedProviderRequest(username, requestId));
    }

    private SwapRequest saveNewRequest(
            CustomerProfile customer,
            BookListing listing) {
        if (customer == null
                || listing == null
                || listing.getStatus() != ListingStatus.AVAILABLE
                || swapRequestRepository
                        .existsByCustomerProfile_CustomerProfileIdAndBookListing_ListingIdAndStatus(
                                customer.getCustomerProfileId(),
                                listing.getListingId(),
                                SwapRequestStatus.PENDING)) {
            return null;
        }

        SwapRequest request = new SwapRequest();
        request.setCustomerProfile(customer);
        request.setBookListing(listing);
        request.setStatus(SwapRequestStatus.PENDING);
        return swapRequestRepository.save(request);
    }

    private SwapRequest getOwnedProviderRequest(
            String username,
            Long requestId) {
        SwapRequest request = swapRequestRepository
                .findById(requestId)
                .orElse(null);

        if (request == null
                || request.getBookListing() == null
                || request.getBookListing().getProviderProfile() == null
                || request.getBookListing().getProviderProfile().getAccount() == null
                || !username.equals(
                        request.getBookListing()
                                .getProviderProfile()
                                .getAccount()
                                .getUsername())) {
            return null;
        }

        return request;
    }

    private SwapRequest approveExistingRequest(SwapRequest request) {
        if (request == null
                || request.getStatus() != SwapRequestStatus.PENDING
                || request.getBookListing() == null
                || request.getBookListing().getStatus() != ListingStatus.AVAILABLE) {
            return null;
        }

        request.approveRequest();

        BookListing listing = request.getBookListing();
        listing.markRequested();
        bookListingRepository.save(listing);

        List<SwapRequest> competingRequests =
                swapRequestRepository.findByBookListing_ListingIdAndStatusIn(
                        listing.getListingId(),
                        List.of(SwapRequestStatus.PENDING));

        for (SwapRequest competingRequest : competingRequests) {
            if (!competingRequest.getRequestId().equals(request.getRequestId())) {
                competingRequest.rejectRequest();
            }
        }

        if (!competingRequests.isEmpty()) {
            swapRequestRepository.saveAll(competingRequests);
        }

        return swapRequestRepository.save(request);
    }

    private SwapRequest rejectExistingRequest(SwapRequest request) {
        if (request == null
                || (request.getStatus() != SwapRequestStatus.PENDING
                && request.getStatus() != SwapRequestStatus.APPROVED)) {
            return null;
        }

        boolean wasApproved =
                request.getStatus() == SwapRequestStatus.APPROVED;

        request.rejectRequest();

        BookListing listing = request.getBookListing();
        if (wasApproved
                && listing != null
                && listing.getStatus() == ListingStatus.REQUESTED) {
            listing.markAvailable();
            bookListingRepository.save(listing);
        }

        return swapRequestRepository.save(request);
    }

    private SwapRequest completeExistingRequest(SwapRequest request) {
        if (request == null
                || request.getStatus() != SwapRequestStatus.APPROVED
                || request.getBookListing() == null
                || request.getBookListing().getStatus() != ListingStatus.REQUESTED) {
            return null;
        }

        request.completeSwap();

        BookListing listing = request.getBookListing();
        listing.markSwapped();
        bookListingRepository.save(listing);

        return swapRequestRepository.save(request);
    }
}