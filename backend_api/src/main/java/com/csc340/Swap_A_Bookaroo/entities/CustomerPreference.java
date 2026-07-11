package com.csc340.Swap_A_Bookaroo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CustomerPreference extends Tag {

    @ManyToOne
    @JsonIgnoreProperties({ "preferences" })
    @JoinColumn
    private CustomerProfile customerProfile;

}
