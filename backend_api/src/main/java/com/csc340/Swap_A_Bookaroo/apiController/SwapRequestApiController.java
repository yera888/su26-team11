package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

@RestController
@RequestMapping("/api/swap-requests")
public class SwapRequestApiController {

    private final SwapRequestService swapRequestService;

    public SwapRequestApiController(SwapRequestService swapRequestService) {
        this.swapRequestService = swapRequestService;
    }

    @PostMapping
    public ResponseEntity<SwapRequest> createSwapRequest(@RequestBody SwapRequest request) {
        SwapRequest created = swapRequestService.createSwapRequest(request);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/customer/{customerProfileId}/pending")
    public ResponseEntity<List<SwapRequest>> getPendingSwapRequestsForCustomer(@PathVariable Long customerProfileId) {
        return ResponseEntity.ok(swapRequestService.getPendingRequestsByCustomer(customerProfileId));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<SwapRequest> getSwapRequestById(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.getSwapRequestById(requestId);
        return swapRequest != null ? ResponseEntity.ok(swapRequest) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{requestId}/approve")
    public ResponseEntity<SwapRequest> approveSwapRequest(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.approveSwapRequest(requestId);
        return swapRequest != null ? ResponseEntity.ok(swapRequest) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<SwapRequest> rejectSwapRequest(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.rejectSwapRequest(requestId);
        return swapRequest != null ? ResponseEntity.ok(swapRequest) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{requestId}/complete")
    public ResponseEntity<SwapRequest> completeSwapRequest(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.completeSwapRequest(requestId);
        return swapRequest != null ? ResponseEntity.ok(swapRequest) : ResponseEntity.notFound().build();
    }
}