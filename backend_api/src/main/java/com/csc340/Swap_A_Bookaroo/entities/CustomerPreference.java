package com.csc340.Swap_A_Bookaroo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerPreference extends Tag {

    @ManyToOne
    @JoinColumn(name = "customer_profile_id")
    @JsonIgnoreProperties({ "preferences", "account" })
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CustomerProfile customerProfile;
}