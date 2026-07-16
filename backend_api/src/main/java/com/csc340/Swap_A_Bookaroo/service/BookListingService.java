package com.csc340.Swap_A_Bookaroo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.repository.*;

@Service
public class BookListingService {

    private static final int MINIMUM_TAGS = 3;
    private final BookListingRepository bookListingRepository;
    private final ProviderProfileRepository providerProfileRepository;
    private final TagRepository tagRepository;

    public BookListingService(BookListingRepository bookListingRepository,
                              ProviderProfileRepository providerProfileRepository,
                              TagRepository tagRepository) {
        this.bookListingRepository = bookListingRepository;
        this.providerProfileRepository = providerProfileRepository;
        this.tagRepository = tagRepository;
    }

    public BookListing getListingById(Long listingId) {
        return bookListingRepository.findById(listingId).orElse(null);
    }

    @Transactional
    public BookListing createListing(Long providerProfileId, BookListing listing) {
        ProviderProfile providerProfile = providerProfileRepository.findById(providerProfileId).orElse(null);
        if (providerProfile == null || !hasEnoughTags(listing.getTagNames())) {
            return null;
        }

        listing.setProviderProfile(providerProfile);
        
        // Attach the managed tags list directly to the entity before saving
        attachTags(listing, listing.getTagNames());
        
        return bookListingRepository.save(listing);
    }

    @Transactional
    public BookListing updateListing(Long listingId, BookListing updatedListing) {
        BookListing existingListing = bookListingRepository.findById(listingId).orElse(null);
        if (existingListing == null) return null;

        existingListing.setTitle(updatedListing.getTitle());
        existingListing.setAuthor(updatedListing.getAuthor());
        existingListing.setIsbn(updatedListing.getIsbn());
        existingListing.setImageLink(updatedListing.getImageLink());

        if (updatedListing.getTagNames() != null) {
            if (!hasEnoughTags(updatedListing.getTagNames())) return null;
            
            attachTags(existingListing, updatedListing.getTagNames());
        }

        return bookListingRepository.save(existingListing);
    }

    public boolean removeListing(Long listingId) {
        BookListing existingListing = bookListingRepository.findById(listingId).orElse(null);
        if (existingListing == null) return false;
        existingListing.markRemoved();
        bookListingRepository.save(existingListing);
        return true;
    }

    private boolean hasEnoughTags(List<String> tagNames) {
        return tagNames != null && tagNames.size() >= MINIMUM_TAGS;
    }

    private void attachTags(BookListing listing, List<String> tagNames) {
        List<Tag> tagList = new ArrayList<>();
        
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByTagName(tagName).orElseGet(() -> {
                Tag newTag = new Tag();
                newTag.setTagName(tagName); 
                return tagRepository.save(newTag);
            });
            tagList.add(tag);
        }
        
        listing.setTags(tagList);
    }
}