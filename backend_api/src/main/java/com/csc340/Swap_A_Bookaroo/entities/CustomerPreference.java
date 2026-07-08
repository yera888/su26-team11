package com.csc340.Swap_A_Bookaroo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class CustomerPreference extends Tag {

    @ManyToOne
    //@JsonIgnoreProperties({ "", })
    @JoinColumn(nullable = false)
    private CustomerPreference customerPreference;

    
}
