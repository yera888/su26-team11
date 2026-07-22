package com.csc340.Swap_A_Bookaroo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.csc340.Swap_A_Bookaroo.entities.CustomerPreference;

@Repository
public interface CustomerPreferenceRepository
        extends JpaRepository<CustomerPreference, Long> {

    Optional<CustomerPreference>
            findByTagIdAndCustomerProfile_Account_Username(
                    Long tagId,
                    String username);

    boolean existsByCustomerProfile_CustomerProfileIdAndTagNameIgnoreCase(
            Long customerProfileId,
            String tagName);
}