package com.csc340.Swap_A_Bookaroo.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "book_listings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(name = "img_url")
    private String img;

    @Column(nullable = false)
    private String status; // "AVAILABLE", "REQUESTED", "SWAPPED"

    @Column(nullable = false)
    private LocalDateTime datePosted = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "provider_profile_id", nullable = false)
    private ProviderProfile providerProfile;

    @ManyToMany
    @JoinTable(
        name = "listing_tags_map",
        joinColumns = @JoinColumn(name = "book_listing_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> listingTags;
}