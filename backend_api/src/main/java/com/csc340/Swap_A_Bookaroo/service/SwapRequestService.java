package com.csc340.Swap_A_Bookaroo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.repository.SwapRequestRepository;

@Service
public class SwapRequestService {

    @Autowired
    private SwapRequestRepository swapRequestRepository;

    public SwapRequest createSwapRequest(SwapRequest request) {
        request.setStatus("PENDING");
        return swapRequestRepository.save(request);
    }

    public List<SwapRequest> getPendingRequestsForCustomer(Long customerProfileId) {
        return swapRequestRepository.findByCustomerProfileIdAndStatus(customerProfileId, "PENDING");
    }
}