package com.csc340.Swap_A_Bookaroo.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "swap_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SwapRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_profile_id", nullable = false)
    private CustomerProfile customerProfile; 

    @ManyToOne
    @JoinColumn(name = "provider_profile_id", nullable = false)
    private ProviderProfile providerProfile; 

    @ManyToOne
    @JoinColumn(name = "book_listing_id", nullable = false)
    private BookListing bookListing; 

    @Column(nullable = false)
    private String status; // "PENDING", "APPROVED", "REJECTED", "COMPLETED"

    private String requestMessage;

    @Column(nullable = false)
    private LocalDate requestDate = LocalDate.now();
}