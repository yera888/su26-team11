package com.csc340.Swap_A_Bookaroo.service;

import org.springframework.stereotype.Service;

import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.repository.BookListingRepository;
import com.csc340.Swap_A_Bookaroo.repository.SwapRequestRepository;

@Service
public class SwapRequestService {

    private final SwapRequestRepository swapRequestRepository;
    private final BookListingRepository bookListingRepository;

    public SwapRequestService(SwapRequestRepository swapRequestRepository,
            BookListingRepository bookListingRepository) {
        this.swapRequestRepository = swapRequestRepository;
        this.bookListingRepository = bookListingRepository;
    }

    public SwapRequest getSwapRequestById(Long requestId) {
        return swapRequestRepository.findById(requestId).orElse(null);
    }

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

        BookListing bookListing = swapRequest.getBookListing();
        bookListing.markAvailable();
        bookListingRepository.save(bookListing);

        return swapRequestRepository.save(swapRequest);
    }

    public SwapRequest completeSwapRequest(Long requestId) {
        SwapRequest swapRequest = swapRequestRepository.findById(requestId).orElse(null);
        if (swapRequest == null) {
            return null;
        }
        swapRequest.completeSwap();

        BookListing bookListing = swapRequest.getBookListing();
        bookListing.markSwapped();
        bookListingRepository.save(bookListing);

        return swapRequestRepository.save(swapRequest);
    }

}
