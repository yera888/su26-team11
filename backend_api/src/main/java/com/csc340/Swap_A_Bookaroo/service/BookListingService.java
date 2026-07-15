package com.csc340.Swap_A_Bookaroo.service;

import java.util.List;
<<<<<<< HEAD

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.entities.ListingTag;
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.entities.Tag;
import com.csc340.Swap_A_Bookaroo.repository.BookListingRepository;
import com.csc340.Swap_A_Bookaroo.repository.ListingTagRepository;
import com.csc340.Swap_A_Bookaroo.repository.ProviderProfileRepository;
import com.csc340.Swap_A_Bookaroo.repository.TagRepository;
=======
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.repository.*;
>>>>>>> origin/main

@Service
public class BookListingService {

    private static final int MINIMUM_TAGS = 3;
<<<<<<< HEAD

=======
>>>>>>> origin/main
    private final BookListingRepository bookListingRepository;
    private final ProviderProfileRepository providerProfileRepository;
    private final TagRepository tagRepository;
    private final ListingTagRepository listingTagRepository;

    public BookListingService(BookListingRepository bookListingRepository,
<<<<<<< HEAD
            ProviderProfileRepository providerProfileRepository,
            TagRepository tagRepository,
            ListingTagRepository listingTagRepository) {
=======
                              ProviderProfileRepository providerProfileRepository,
                              TagRepository tagRepository,
                              ListingTagRepository listingTagRepository) {
>>>>>>> origin/main
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
<<<<<<< HEAD
        if (existingListing == null) {
            return null;
        }
=======
        if (existingListing == null) return null;
>>>>>>> origin/main

        existingListing.setTitle(updatedListing.getTitle());
        existingListing.setAuthor(updatedListing.getAuthor());
        existingListing.setIsbn(updatedListing.getIsbn());
        existingListing.setImageLink(updatedListing.getImageLink());

        if (updatedListing.getTagNames() != null) {
<<<<<<< HEAD
            if (!hasEnoughTags(updatedListing.getTagNames())) {
                return null;
            }
=======
            if (!hasEnoughTags(updatedListing.getTagNames())) return null;
>>>>>>> origin/main
            listingTagRepository.deleteByBookListing_ListingId(listingId);
            attachTags(existingListing, updatedListing.getTagNames());
        }

        return bookListingRepository.save(existingListing);
    }

    public boolean removeListing(Long listingId) {
        BookListing existingListing = bookListingRepository.findById(listingId).orElse(null);
<<<<<<< HEAD
        if (existingListing == null) {
            return false;
        }
=======
        if (existingListing == null) return false;
>>>>>>> origin/main
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
<<<<<<< HEAD

}
=======
}
>>>>>>> origin/main
