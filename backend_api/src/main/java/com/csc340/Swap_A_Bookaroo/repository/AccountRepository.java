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
import com.csc340.Swap_A_Bookaroo.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
<<<<<<< HEAD

    Optional<Account> findByUsername(String username);

}
=======
    Optional<Account> findByUsername(String username);
}
>>>>>>> origin/main
