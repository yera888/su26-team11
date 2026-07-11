package com.csc340.Swap_A_Bookaroo.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile extends Account {

    @Column(columnDefinition = "TEXT")
    private String bio;
    // Constructor
    public CustomerProfile(String bio) {
        this.bio = bio;
    }

    // Methods
    public void createCustomerProfile(String bio) {
        this.bio = bio;
    }

    public void updateCustomerProfile(String bio) {
        this.bio = bio;
    }

    public void addpreference(Tag tag) {
        // Add preference logic here
    }

    public void removepreference(Tag tag) {
        // Remove preference logic here
    }

    public void viewMatchedFeed(){
        // View matched feed logic here
    }

    public void requestSwap(BookListing listing) {
        // Request swap logic here
    }

    public void viewPendingRequests() {
        // View pending requests logic here
    }

    @OneToMany(mappedBy = "customerProfile")
    @JsonIgnoreProperties({"customer"})
    private List<CustomerPreference> preferences;
}
