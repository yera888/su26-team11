package com.csc340.Swap_A_Bookaroo.entities;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "swap_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwapRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "customer_profile_id", nullable = false)
    @JsonIgnoreProperties({ "preferences" })
    private CustomerProfile customerProfile;

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    @JsonIgnoreProperties({ "providerProfile" })
    private BookListing bookListing;

    @Enumerated(EnumType.STRING)
    private SwapRequestStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date requestDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date responseDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date completedDate;

    @PrePersist
    protected void onCreate() {
        this.requestDate = new Date();
        if (this.status == null) {
            this.status = SwapRequestStatus.PENDING;
        }
    }

    public void approveRequest() {
        this.status = SwapRequestStatus.APPROVED;
        this.responseDate = new Date();
    }

    public void rejectRequest() {
        this.status = SwapRequestStatus.REJECTED;
        this.responseDate = new Date();
    }

    public void cancelRequest() { this.status = SwapRequestStatus.CANCELLED; }
    public void completeSwap() {
        this.status = SwapRequestStatus.COMPLETED;
        this.completedDate = new Date();
    }
}