package com.csc340.Swap_A_Bookaroo.apiController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

@RestController
@RequestMapping("/api/swap-requests")
public class SwapRequestApiController {

    private final SwapRequestService swapRequestService;

    public SwapRequestApiController(SwapRequestService swapRequestService) {
        this.swapRequestService = swapRequestService;
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<SwapRequest> getSwapRequestById(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.getSwapRequestById(requestId);
        if (swapRequest != null) {
            return ResponseEntity.ok(swapRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{requestId}/approve")
    public ResponseEntity<SwapRequest> approveSwapRequest(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.approveSwapRequest(requestId);
        if (swapRequest != null) {
            return ResponseEntity.ok(swapRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<SwapRequest> rejectSwapRequest(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.rejectSwapRequest(requestId);
        if (swapRequest != null) {
            return ResponseEntity.ok(swapRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{requestId}/complete")
    public ResponseEntity<SwapRequest> completeSwapRequest(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.completeSwapRequest(requestId);
        if (swapRequest != null) {
            return ResponseEntity.ok(swapRequest);
        }
        return ResponseEntity.notFound().build();
    }

}
