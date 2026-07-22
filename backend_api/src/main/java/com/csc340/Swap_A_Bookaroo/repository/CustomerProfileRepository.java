package com.csc340.Swap_A_Bookaroo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.csc340.Swap_A_Bookaroo.entities.CustomerProfile;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
    Optional<CustomerProfile> findByAccount_AccountId(Long accountId);
    Optional<CustomerProfile> findByAccount_Username(String username);
}
