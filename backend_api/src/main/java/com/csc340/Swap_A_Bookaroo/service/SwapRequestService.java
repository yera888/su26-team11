package com.csc340.Swap_A_Bookaroo.service;

<<<<<<< HEAD
import org.springframework.stereotype.Service;

import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.repository.BookListingRepository;
import com.csc340.Swap_A_Bookaroo.repository.SwapRequestRepository;
=======
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.repository.*;
>>>>>>> origin/main

@Service
public class SwapRequestService {

    private final SwapRequestRepository swapRequestRepository;
    private final BookListingRepository bookListingRepository;
<<<<<<< HEAD

    public SwapRequestService(SwapRequestRepository swapRequestRepository,
            BookListingRepository bookListingRepository) {
        this.swapRequestRepository = swapRequestRepository;
        this.bookListingRepository = bookListingRepository;
=======
    private final CustomerProfileRepository customerProfileRepository;

    public SwapRequestService(SwapRequestRepository swapRequestRepository,
                              BookListingRepository bookListingRepository,
                              CustomerProfileRepository customerProfileRepository) {
        this.swapRequestRepository = swapRequestRepository;
        this.bookListingRepository = bookListingRepository;
        this.customerProfileRepository = customerProfileRepository;
>>>>>>> origin/main
    }

    public SwapRequest getSwapRequestById(Long requestId) {
        return swapRequestRepository.findById(requestId).orElse(null);
    }

<<<<<<< HEAD
    public SwapRequest approveSwapRequest(Long requestId) {
        SwapRequest swapRequest = swapRequestRepository.findById(requestId).orElse(null);
        if (swapRequest == null) {
            return null;
        }
        swapRequest.approveRequest();
        return swapRequestRepository.save(swapRequest);
    }

    public SwapRequest rejectSwapRequest(Long requestId) {
        SwapRequest swapRequest = swapRequestRepository.findById(requestId).orElse(null);
        if (swapRequest == null) {
            return null;
        }
        swapRequest.rejectRequest();

=======
    public List<SwapRequest> getPendingRequestsByCustomer(Long customerProfileId) {
        return swapRequestRepository.findByCustomerProfile_CustomerProfileIdAndStatus(customerProfileId, SwapRequestStatus.PENDING);
    }

    @Transactional
    public SwapRequest createSwapRequest(SwapRequest request) {
        CustomerProfile customer = customerProfileRepository.findById(request.getCustomerProfile().getCustomerProfileId()).orElse(null);
        BookListing listing = bookListingRepository.findById(request.getBookListing().getListingId()).orElse(null);
        
        if (customer == null || listing == null || listing.getStatus() != ListingStatus.AVAILABLE) {
            return null;
        }

        request.setCustomerProfile(customer);
        request.setBookListing(listing);
        request.setStatus(SwapRequestStatus.PENDING);
        
        return swapRequestRepository.save(request);
    }

    @Transactional
    public SwapRequest approveSwapRequest(Long requestId) {
        SwapRequest swapRequest = swapRequestRepository.findById(requestId).orElse(null);
        if (swapRequest == null) return null;

        swapRequest.approveRequest();
        BookListing bookListing = swapRequest.getBookListing();
        bookListing.markRequested();
        bookListingRepository.save(bookListing);

        return swapRequestRepository.save(swapRequest);
    }

    @Transactional
    public SwapRequest rejectSwapRequest(Long requestId) {
        SwapRequest swapRequest = swapRequestRepository.findById(requestId).orElse(null);
        if (swapRequest == null) return null;

        swapRequest.rejectRequest();
>>>>>>> origin/main
        BookListing bookListing = swapRequest.getBookListing();
        bookListing.markAvailable();
        bookListingRepository.save(bookListing);

        return swapRequestRepository.save(swapRequest);
    }

<<<<<<< HEAD
    public SwapRequest completeSwapRequest(Long requestId) {
        SwapRequest swapRequest = swapRequestRepository.findById(requestId).orElse(null);
        if (swapRequest == null) {
            return null;
        }
        swapRequest.completeSwap();

=======
    @Transactional
    public SwapRequest completeSwapRequest(Long requestId) {
        SwapRequest swapRequest = swapRequestRepository.findById(requestId).orElse(null);
        if (swapRequest == null) return null;

        swapRequest.completeSwap();
>>>>>>> origin/main
        BookListing bookListing = swapRequest.getBookListing();
        bookListing.markSwapped();
        bookListingRepository.save(bookListing);

        return swapRequestRepository.save(swapRequest);
    }
<<<<<<< HEAD

}
=======
}
>>>>>>> origin/main
