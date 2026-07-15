package com.csc340.Swap_A_Bookaroo.apiController;

<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

=======
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
>>>>>>> origin/main
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

@RestController
@RequestMapping("/api/swap-requests")
public class SwapRequestApiController {

    private final SwapRequestService swapRequestService;

    public SwapRequestApiController(SwapRequestService swapRequestService) {
        this.swapRequestService = swapRequestService;
    }

<<<<<<< HEAD
    @GetMapping("/{requestId}")
    public ResponseEntity<SwapRequest> getSwapRequestById(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.getSwapRequestById(requestId);
        if (swapRequest != null) {
            return ResponseEntity.ok(swapRequest);
        }
        return ResponseEntity.notFound().build();
=======
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
>>>>>>> origin/main
    }

    @PutMapping("/{requestId}/approve")
    public ResponseEntity<SwapRequest> approveSwapRequest(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.approveSwapRequest(requestId);
<<<<<<< HEAD
        if (swapRequest != null) {
            return ResponseEntity.ok(swapRequest);
        }
        return ResponseEntity.notFound().build();
=======
        return swapRequest != null ? ResponseEntity.ok(swapRequest) : ResponseEntity.notFound().build();
>>>>>>> origin/main
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<SwapRequest> rejectSwapRequest(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.rejectSwapRequest(requestId);
<<<<<<< HEAD
        if (swapRequest != null) {
            return ResponseEntity.ok(swapRequest);
        }
        return ResponseEntity.notFound().build();
=======
        return swapRequest != null ? ResponseEntity.ok(swapRequest) : ResponseEntity.notFound().build();
>>>>>>> origin/main
    }

    @PutMapping("/{requestId}/complete")
    public ResponseEntity<SwapRequest> completeSwapRequest(@PathVariable Long requestId) {
        SwapRequest swapRequest = swapRequestService.completeSwapRequest(requestId);
<<<<<<< HEAD
        if (swapRequest != null) {
            return ResponseEntity.ok(swapRequest);
        }
        return ResponseEntity.notFound().build();
    }

}
=======
        return swapRequest != null ? ResponseEntity.ok(swapRequest) : ResponseEntity.notFound().build();
    }
}
>>>>>>> origin/main
