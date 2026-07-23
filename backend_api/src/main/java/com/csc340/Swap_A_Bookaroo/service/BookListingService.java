package com.csc340.Swap_A_Bookaroo.service;

import java.util.Arrays;
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
    private final SwapRequestRepository swapRequestRepository;

    public BookListingService(
            BookListingRepository bookListingRepository,
            ProviderProfileRepository providerProfileRepository,
            TagRepository tagRepository,
            ListingTagRepository listingTagRepository,
            SwapRequestRepository swapRequestRepository) {
        this.bookListingRepository = bookListingRepository;
        this.providerProfileRepository = providerProfileRepository;
        this.tagRepository = tagRepository;
        this.listingTagRepository = listingTagRepository;
        this.swapRequestRepository = swapRequestRepository;
    }

    public BookListing getListingById(Long listingId) {
        return bookListingRepository.findById(listingId).orElse(null);
    }

    public BookListing getListingForProvider(Long listingId, String username) {
        return bookListingRepository
                .findByListingIdAndProviderProfile_Account_Username(listingId, username)
                .orElse(null);
    }

    public List<BookListing> getActiveListingsForProvider(String username) {
        return bookListingRepository
                .findByProviderProfile_Account_UsernameAndStatusInOrderByDatePostedDesc(
                        username,
                        Arrays.asList(ListingStatus.AVAILABLE, ListingStatus.REQUESTED));
    }

    @Transactional
    public BookListing createListing(Long providerProfileId, BookListing listing) {
        ProviderProfile providerProfile =
                providerProfileRepository.findById(providerProfileId).orElse(null);
        return createListingForProfile(providerProfile, listing);
    }

    @Transactional
    public BookListing createListingForProvider(String username, BookListing listing) {
        ProviderProfile providerProfile =
                providerProfileRepository.findByAccount_Username(username).orElse(null);
        return createListingForProfile(providerProfile, listing);
    }

    @Transactional
    public BookListing updateListing(Long listingId, BookListing updatedListing) {
        BookListing existingListing =
                bookListingRepository.findById(listingId).orElse(null);
        return updateExistingListing(existingListing, updatedListing);
    }

    @Transactional
    public BookListing updateListingForProvider(
            String username,
            Long listingId,
            BookListing updatedListing) {
        BookListing existingListing = getListingForProvider(listingId, username);
        return updateExistingListing(existingListing, updatedListing);
    }

    @Transactional
    public boolean removeListing(Long listingId) {
        BookListing existingListing =
                bookListingRepository.findById(listingId).orElse(null);
        return removeExistingListing(existingListing);
    }

    @Transactional
    public boolean removeListingForProvider(String username, Long listingId) {
        BookListing existingListing = getListingForProvider(listingId, username);
        return removeExistingListing(existingListing);
    }

    private BookListing createListingForProfile(
            ProviderProfile providerProfile,
            BookListing listing) {
        List<String> tagNames = normalizedTags(
                listing == null ? null : listing.getTagNames());

        if (providerProfile == null || listing == null || !hasEnoughTags(tagNames)) {
            return null;
        }

        listing.setProviderProfile(providerProfile);
        listing.setTagNames(tagNames);
        BookListing savedListing = bookListingRepository.save(listing);
        attachTags(savedListing, tagNames);

        return bookListingRepository.findById(savedListing.getListingId())
                .orElse(savedListing);
    }

    private BookListing updateExistingListing(
            BookListing existingListing,
            BookListing updatedListing) {
        if (existingListing == null
                || updatedListing == null
                || existingListing.getStatus() == ListingStatus.SWAPPED
                || existingListing.getStatus() == ListingStatus.REMOVED) {
            return null;
        }

        List<String> tagNames = normalizedTags(updatedListing.getTagNames());
        if (!hasEnoughTags(tagNames)) {
            return null;
        }

        existingListing.setTitle(updatedListing.getTitle());
        existingListing.setAuthor(updatedListing.getAuthor());
        existingListing.setIsbn(updatedListing.getIsbn());
        existingListing.setImageLink(updatedListing.getImageLink());

        listingTagRepository.deleteByBookListing_ListingId(
                existingListing.getListingId());
        attachTags(existingListing, tagNames);
        bookListingRepository.save(existingListing);

        return bookListingRepository.findById(existingListing.getListingId())
                .orElse(existingListing);
    }

    private boolean removeExistingListing(BookListing existingListing) {
        if (existingListing == null
                || existingListing.getStatus() == ListingStatus.SWAPPED
                || existingListing.getStatus() == ListingStatus.REMOVED) {
            return false;
        }

        List<SwapRequest> openRequests =
                swapRequestRepository.findByBookListing_ListingIdAndStatusIn(
                        existingListing.getListingId(),
                        Arrays.asList(
                                SwapRequestStatus.PENDING,
                                SwapRequestStatus.APPROVED));

        for (SwapRequest request : openRequests) {
            request.rejectRequest();
        }
        if (!openRequests.isEmpty()) {
            swapRequestRepository.saveAll(openRequests);
        }

        existingListing.markRemoved();
        bookListingRepository.save(existingListing);
        return true;
    }

    private List<String> normalizedTags(List<String> tagNames) {
        if (tagNames == null) {
            return List.of();
        }

        return tagNames.stream()
                .filter(tagName -> tagName != null && !tagName.isBlank())
                .map(String::trim)
                .distinct()
                .toList();
    }

    private boolean hasEnoughTags(List<String> tagNames) {
        return tagNames.size() >= MINIMUM_TAGS;
    }

    private void attachTags(BookListing listing, List<String> tagNames) {
        List<Tag> tagList = new ArrayList<>();
        
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByTagName(tagName)
                    .orElseGet(() -> {
                        Tag newTag = new Tag();
                        newTag.createTag(tagName);
                        return tagRepository.save(newTag);
                    });

            ListingTag listingTag = new ListingTag();
            listingTag.setBookListing(listing);
            listingTag.addTag(tag);
            listingTagRepository.save(listingTag);
        }
        
        listing.setTags(tagList);
    }
}