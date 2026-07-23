package com.csc340.Swap_A_Bookaroo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.csc340.Swap_A_Bookaroo.entities.CustomerPreference;

@Repository
public interface CustomerPreferenceRepository extends JpaRepository<CustomerPreference, Long> {

    boolean existsByCustomerProfile_CustomerProfileIdAndTag_TagNameIgnoreCase(
            Long customerProfileId, String tagName);

    Optional<CustomerPreference> findByCustomerProfile_Account_UsernameAndTag_TagNameIgnoreCase(
            String username, String tagName);

    List<CustomerPreference> findByCustomerProfile_Account_Username(String username);
}