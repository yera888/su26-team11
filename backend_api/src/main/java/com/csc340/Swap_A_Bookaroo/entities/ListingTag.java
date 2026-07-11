package com.csc340.Swap_A_Bookaroo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "listing_tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingTagId;

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    @JsonIgnoreProperties({ "listingTags", "providerProfile" })
    private BookListing bookListing;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public void addTag(Tag tag) {
        this.tag = tag;
    }

    public void removeTag() {
        this.tag = null;
    }

}
