package com.csc340.Swap_A_Bookaroo.entities;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_listings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;

    @ManyToOne
    @JoinColumn(name = "provider_profile_id", nullable = false)
    @JsonIgnoreProperties({ "bookListings", "account" })
    private ProviderProfile providerProfile;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @JsonProperty("IMG")
    private String imageLink;

    @Enumerated(EnumType.STRING)
    private ListingStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date datePosted;

    @OneToMany(mappedBy = "bookListing", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({ "bookListing" })
    private List<ListingTag> listingTags;

    @Transient
    private List<String> tagNames;

    @PrePersist
    protected void onCreate() {
        this.datePosted = new Date();
        if (this.status == null) {
            this.status = ListingStatus.AVAILABLE;
        }
    }

    public void markRequested() { this.status = ListingStatus.REQUESTED; }
    public void markAvailable() { this.status = ListingStatus.AVAILABLE; }
    public void markSwapped() { this.status = ListingStatus.SWAPPED; }
    public void markRemoved() { this.status = ListingStatus.REMOVED; }
}