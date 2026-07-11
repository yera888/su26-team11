package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

@RestController
@RequestMapping("/api/swap-requests")
public class SwapRequestController {

    @Autowired
    private SwapRequestService swapRequestService;

    @PostMapping
    public ResponseEntity<SwapRequest> createSwapRequest(@RequestBody SwapRequest request) {
        return ResponseEntity.ok(swapRequestService.createSwapRequest(request));
    }

    @GetMapping("/customer/{customerProfileId}/pending")
    public ResponseEntity<List<SwapRequest>> getPendingSwapRequests(@PathVariable Long customerProfileId) {
        return ResponseEntity.ok(swapRequestService.getPendingRequestsForCustomer(customerProfileId));
    }
}