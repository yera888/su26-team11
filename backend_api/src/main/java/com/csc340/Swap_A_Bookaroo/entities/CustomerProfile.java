package com.csc340.Swap_A_Bookaroo.entities;

import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerProfileId;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "accountId", nullable = false)
    @JsonIgnoreProperties({ "password", "role" })
    private Account account;

    @Column(columnDefinition = "TEXT")
    private String bio;

    // This defines the join table that connects Customers directly to Master Tags
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "customer_tags",
        joinColumns = @JoinColumn(name = "customer_profile_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> preferences = new HashSet<>();

    // old method for when there was a CustomerPreference class
    // The customerProfile now connects directly to the tag as a many to many
    /*@OneToMany(mappedBy = "customerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customerProfile"})
    private List<CustomerPreference> preferences1; */
}
