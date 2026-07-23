package com.csc340.Swap_A_Bookaroo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_profile_id", nullable = false)
    @JsonIgnoreProperties({"preferences", "account"})
    private CustomerProfile customerProfile;

    // Reference to the Tag table!
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    // Helper getter so templates can still call pref.tagName
    public String getTagName() {
        return tag != null ? tag.getTagName() : null;
    }
}