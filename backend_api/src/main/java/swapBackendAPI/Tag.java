package swapBackendAPI;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Tag {

    // Data fields 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private String tagName;

    // Methods
    public void createTag(String tagName) {
        this.tagName = tagName;
    }

}
