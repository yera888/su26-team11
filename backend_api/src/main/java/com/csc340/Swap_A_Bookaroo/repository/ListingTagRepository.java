package com.csc340.Swap_A_Bookaroo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc340.Swap_A_Bookaroo.entities.ListingTag;

@Repository
public interface ListingTagRepository extends JpaRepository<ListingTag, Long> {

    List<ListingTag> findByBookListing_ListingId(Long listingId);

    void deleteByBookListing_ListingId(Long listingId);

}
