package com.csc340.Swap_A_Bookaroo.entities;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
=======
import jakarta.persistence.*;
>>>>>>> origin/main
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

<<<<<<< HEAD
    // Data fields
=======
>>>>>>> origin/main
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private String tagName;

<<<<<<< HEAD
    // Methods
    public void createTag(String tagName) {
        this.tagName = tagName;
    }

    public void updateTag(String tagName) {
        this.tagName = tagName;
    }

}
=======
    public void createTag(String tagName) { this.tagName = tagName; }
    public void updateTag(String tagName) { this.tagName = tagName; }
}
>>>>>>> origin/main
