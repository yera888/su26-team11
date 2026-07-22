package com.csc340.Swap_A_Bookaroo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.csc340.Swap_A_Bookaroo.entities.*;

@Repository
public interface BookListingRepository extends JpaRepository<BookListing, Long> {
    List<BookListing> findByProviderProfile_ProviderProfileId(Long providerProfileId);
    List<BookListing> findByProviderProfile_ProviderProfileIdAndStatusIn(Long providerProfileId, List<ListingStatus> statuses);
    List<BookListing> findByStatus(ListingStatus status);
    List<BookListing> findDistinctByStatusAndTagsIn(ListingStatus status, List<Tag> tags);
}
