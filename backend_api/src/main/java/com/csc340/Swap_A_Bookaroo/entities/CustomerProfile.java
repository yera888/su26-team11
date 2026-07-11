package com.csc340.Swap_A_Bookaroo.entities;

import java.util.List;
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