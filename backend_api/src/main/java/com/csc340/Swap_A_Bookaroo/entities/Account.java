package com.csc340.Swap_A_Bookaroo.entities;

<<<<<<< HEAD
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
=======
import jakarta.persistence.*;
>>>>>>> origin/main
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
<<<<<<< HEAD
@Table(name = "Profiles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
=======
@Table(name = "accounts")
>>>>>>> origin/main
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
<<<<<<< HEAD

}
=======
}
>>>>>>> origin/main
