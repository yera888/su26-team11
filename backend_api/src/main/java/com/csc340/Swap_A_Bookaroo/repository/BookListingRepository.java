package com.csc340.Swap_A_Bookaroo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc340.Swap_A_Bookaroo.entities.BookListing;

@Repository
public interface BookListingRepository extends JpaRepository<BookListing, Long> {

    // Fetches all books uploaded by a specific provider
    List<BookListing> findByProviderProfileId(Long providerProfileId);

    // Fetches books filtered by status (e.g., "AVAILABLE", "REQUESTED")
    // This will be incredibly useful later for your matching feed logic!
    List<BookListing> findByStatus(String status);
}