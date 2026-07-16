package com.csc340.Swap_A_Bookaroo.service;

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
    private final ListingTagRepository listingTagRepository;

    public BookListingService(BookListingRepository bookListingRepository,
                              ProviderProfileRepository providerProfileRepository,
                              TagRepository tagRepository,
                              ListingTagRepository listingTagRepository) {
        this.bookListingRepository = bookListingRepository;
        this.providerProfileRepository = providerProfileRepository;
        this.tagRepository = tagRepository;
        this.listingTagRepository = listingTagRepository;
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
        BookListing savedListing = bookListingRepository.save(listing);
        attachTags(savedListing, listing.getTagNames());

        return bookListingRepository.findById(savedListing.getListingId()).orElse(savedListing);
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
            listingTagRepository.deleteByBookListing_ListingId(listingId);
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
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByTagName(tagName).orElseGet(() -> {
                Tag newTag = new Tag();
                newTag.createTag(tagName);
                return tagRepository.save(newTag);
            });
            ListingTag listingTag = new ListingTag();
            listingTag.setBookListing(listing);
            listingTag.addTag(tag);
            listingTagRepository.save(listingTag);
        }
    }
}
