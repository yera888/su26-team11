package com.csc340.Swap_A_Bookaroo.entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
    @JsonIgnoreProperties({ "password", "role" })
    private Account account;

    private int swapCreditBalance;

    @OneToMany(mappedBy = "providerProfile", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({ "providerProfile" })
    private List<BookListing> bookListings;
}