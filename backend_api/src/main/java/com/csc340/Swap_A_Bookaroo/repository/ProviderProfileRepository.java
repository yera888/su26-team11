package com.csc340.Swap_A_Bookaroo.repository;
<<<<<<< HEAD

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

=======
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
>>>>>>> origin/main
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;

@Repository
public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Long> {
<<<<<<< HEAD

    Optional<ProviderProfile> findByAccount_AccountId(Long accountId);

}
=======
    Optional<ProviderProfile> findByAccount_AccountId(Long accountId);
}
>>>>>>> origin/main
