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
import com.csc340.Swap_A_Bookaroo.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
<<<<<<< HEAD

    Optional<Tag> findByTagName(String tagName);

}
=======
    Optional<Tag> findByTagName(String tagName);
}
>>>>>>> origin/main
