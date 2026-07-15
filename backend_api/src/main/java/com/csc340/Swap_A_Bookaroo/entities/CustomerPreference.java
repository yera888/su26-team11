package com.csc340.Swap_A_Bookaroo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
<<<<<<< HEAD

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
=======
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerPreference extends Tag {

    @ManyToOne
    @JoinColumn(name = "customer_profile_id")
    @JsonIgnoreProperties({ "preferences", "account" })
    private CustomerProfile customerProfile;
}
>>>>>>> origin/main
