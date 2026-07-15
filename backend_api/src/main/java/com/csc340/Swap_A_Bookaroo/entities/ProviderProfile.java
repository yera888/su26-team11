package com.csc340.Swap_A_Bookaroo.entities;

import java.util.List;
<<<<<<< HEAD

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
=======
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
>>>>>>> origin/main
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "provider_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long providerProfileId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
<<<<<<< HEAD
    @JsonIgnoreProperties({ "password", "description" })
    private Account account;

    private int swapCreditBalance;

    @OneToMany(mappedBy = "providerProfile")
    @JsonIgnoreProperties({ "providerProfile" })
    private List<BookListing> bookListings;

    public ProviderProfile(Account account) {
        this.account = account;
        this.swapCreditBalance = 0;
    }

}

=======
    @JsonIgnoreProperties({ "password", "role" })
    private Account account;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private int swapCreditBalance;

    @OneToMany(mappedBy = "providerProfile", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({ "providerProfile" })
    private List<BookListing> bookListings;
}
>>>>>>> origin/main
