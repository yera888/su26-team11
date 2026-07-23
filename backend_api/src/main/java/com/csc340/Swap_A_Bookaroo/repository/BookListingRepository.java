package com.csc340.Swap_A_Bookaroo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.entities.ListingStatus;

@Repository
public interface BookListingRepository extends JpaRepository<BookListing, Long> {

    List<BookListing> findByProviderProfile_ProviderProfileId(Long providerProfileId);

    List<BookListing> findByProviderProfile_ProviderProfileIdAndStatusIn(
            Long providerProfileId,
            List<ListingStatus> statuses);

    List<BookListing> findByProviderProfile_Account_UsernameAndStatusInOrderByDatePostedDesc(
            String username,
            List<ListingStatus> statuses);

    Optional<BookListing> findByListingIdAndProviderProfile_Account_Username(
            Long listingId,
            String username);

    List<BookListing> findByStatus(ListingStatus status);
    //List<BookListing> findDistinctByStatusAndTagsIn(ListingStatus status, List<Tag> tags);
}
