package com.csc340.Swap_A_Bookaroo.entities;

import java.util.List;
<<<<<<< HEAD

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
=======
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
>>>>>>> origin/main
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
<<<<<<< HEAD
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile extends Account {

    // Methods
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






=======
@Table(name = "customer_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerProfileId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnoreProperties({ "password", "role" })
    private Account account;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @OneToMany(mappedBy = "customerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customerProfile"})
    private List<CustomerPreference> preferences;
}
>>>>>>> origin/main
